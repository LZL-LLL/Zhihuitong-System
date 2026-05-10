package com.zhihuitong.system.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhihuitong.common.utils.StringUtils;
import com.zhihuitong.common.utils.SecurityUtils;
import com.zhihuitong.common.core.domain.entity.SysUser;
import com.zhihuitong.system.domain.Score;
import com.zhihuitong.system.domain.SysUserRole;
import com.zhihuitong.system.domain.Student;
import com.zhihuitong.system.mapper.ScoreMapper;
import com.zhihuitong.system.mapper.StudentMapper;
import com.zhihuitong.system.mapper.SysUserMapper;
import com.zhihuitong.system.mapper.SysUserRoleMapper;
import com.zhihuitong.system.service.IStudentService;

/**
 * 学生管理Service实现类
 *
 * @author ruoyi
 */
@Service
public class StudentServiceImpl implements IStudentService
{
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    private static final SecureRandom RANDOM = new SecureRandom();

    /** 默认课程列表（当课表中无课程时使用） */
    private static final String[] DEFAULT_COURSES = {
        "软件工程导论", "面向对象程序设计(Java)", "数据结构与算法",
        "数据库系统原理", "操作系统", "计算机网络",
        "软件需求工程", "软件测试技术", "软件项目管理", "Web应用开发"
    };

    /**
     * 查询学生列表
     */
    @Override
    public List<Student> selectStudentList(Student student)
    {
        return studentMapper.selectStudentList(student);
    }

    /**
     * 通过学号查询学生
     */
    @Override
    public Student selectStudentByNo(String studentNo)
    {
        return studentMapper.selectStudentByNo(studentNo);
    }

    /**
     * 新增学生信息，自动创建关联的系统用户账号、
     * 分配普通角色，并根据课表自动生成随机成绩
     */
    @Override
    @Transactional
    public int insertStudent(Student student)
    {
        // 1. 创建关联的系统用户账号
        SysUser user = new SysUser();
        user.setUserName(student.getStudentNo());
        user.setNickName(student.getStudentName());
        user.setPassword(SecurityUtils.encryptPassword("123456"));
        user.setEmail(student.getEmail());
        user.setPhonenumber(student.getPhone());
        user.setSex(student.getGender());
        user.setStatus("0");
        user.setCreateBy(student.getCreateBy());
        userMapper.insertUser(user);

        // 2. 分配普通角色（role_id=2, role_key=common）
        Long userId = user.getUserId();
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(2L);
        List<SysUserRole> roleList = new ArrayList<>();
        roleList.add(userRole);
        userRoleMapper.batchUserRole(roleList);

        // 3. 关联用户ID到学生
        student.setUserId(userId);
        student.setStatus("0");
        int result = studentMapper.insertStudent(student);

        // 4. 根据课表自动生成随机成绩
        String studentNo = student.getStudentNo();
        List<String> courseNames = studentMapper.selectCourseNamesByStudentNo(studentNo);
        if (courseNames == null || courseNames.isEmpty())
        {
            // 如果课表中无课程，使用默认课程列表
            courseNames = List.of(DEFAULT_COURSES);
        }
        // 2025-2026学年日期范围
        LocalDate semesterStart = LocalDate.of(2025, 9, 1);
        LocalDate semesterEnd = LocalDate.of(2026, 7, 31);
        long totalDays = semesterStart.until(semesterEnd).getDays();

        for (String courseName : courseNames)
        {
            Score score = new Score();
            score.setStudentNo(studentNo);
            score.setCourseName(courseName);
            // 随机生成 30~100 之间的成绩
            int randomScore = RANDOM.nextInt(71) + 30;
            score.setScoreValue(BigDecimal.valueOf(randomScore));
            score.setFullScore(BigDecimal.valueOf(100));
            score.setExamType(String.valueOf(RANDOM.nextInt(4)));
            // 随机生成 2025-2026 学年内的考试时间
            LocalDate randomDate = semesterStart.plusDays(RANDOM.nextLong(totalDays));
            score.setExamDate(java.util.Date.from(randomDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            scoreMapper.insertScore(score);
        }

        return result;
    }

    /**
     * 修改学生信息
     */
    @Override
    public int updateStudent(Student student)
    {
        return studentMapper.updateStudent(student);
    }

    /**
     * 删除学生信息，同时删除关联用户账号和成绩
     */
    @Override
    @Transactional
    public int deleteStudentByNo(String studentNo)
    {
        Student student = studentMapper.selectStudentByNo(studentNo);
        // 删除关联的成绩信息
        scoreMapper.deleteScoreByStudentNo(studentNo);
        // 删除关联的用户账号
        if (student != null && student.getUserId() != null)
        {
            userMapper.deleteUserById(student.getUserId());
        }
        return studentMapper.deleteStudentByNo(studentNo);
    }

    /**
     * 批量删除学生信息
     */
    @Override
    @Transactional
    public int deleteStudentByNos(String[] studentNos)
    {
        for (String studentNo : studentNos)
        {
            Student student = studentMapper.selectStudentByNo(studentNo);
            // 删除关联的成绩信息
            scoreMapper.deleteScoreByStudentNo(studentNo);
            // 删除关联的用户账号
            if (student != null && student.getUserId() != null)
            {
                userMapper.deleteUserById(student.getUserId());
            }
        }
        return studentMapper.deleteStudentByNos(studentNos);
    }

    /**
     * 校验学号是否唯一
     */
    @Override
    public boolean checkStudentNoUnique(Student student)
    {
        Student info = studentMapper.selectStudentByNo(student.getStudentNo());
        // 修改时排除自身：通过 compare 的 studentNo 和数据库的 studentNo 比较
        // 由于主键就是 studentNo，修改操作不会改变 studentNo，因此只需检查是否存在
        return info == null;
    }
}
