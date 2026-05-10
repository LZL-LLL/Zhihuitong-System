package com.zhihuitong.system.service;

import java.util.List;
import com.zhihuitong.system.domain.Student;

/**
 * 学生管理Service接口
 *
 * @author ruoyi
 */
public interface IStudentService
{
    /**
     * 查询学生列表
     *
     * @param student 学生信息
     * @return 学生集合
     */
    public List<Student> selectStudentList(Student student);

    /**
     * 通过学号查询学生
     *
     * @param studentNo 学号
     * @return 学生对象信息
     */
    public Student selectStudentByNo(String studentNo);

    /**
     * 新增学生信息
     *
     * @param student 学生信息
     * @return 结果
     */
    public int insertStudent(Student student);

    /**
     * 修改学生信息
     *
     * @param student 学生信息
     * @return 结果
     */
    public int updateStudent(Student student);

    /**
     * 删除学生信息
     *
     * @param studentNo 学号
     * @return 结果
     */
    public int deleteStudentByNo(String studentNo);

    /**
     * 批量删除学生信息
     *
     * @param studentNos 需要删除的学号数组
     * @return 结果
     */
    public int deleteStudentByNos(String[] studentNos);

    /**
     * 校验学号是否唯一
     *
     * @param student 学生信息
     * @return 结果
     */
    public boolean checkStudentNoUnique(Student student);
}
