package com.zhihuitong.system.domain;

import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhihuitong.common.annotation.Excel;
import com.zhihuitong.common.annotation.Excel.ColumnType;
import com.zhihuitong.common.core.domain.BaseEntity;

/**
 * 学生信息实体
 *
 * @author ruoyi
 */
public class Student extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 关联系统用户ID */
    private Long userId;

    /** 学号（主键） */
    @Excel(name = "学号")
    @NotBlank(message = "学号不能为空")
    @Size(max = 50, message = "学号长度不能超过50个字符")
    private String studentNo;

    /** 姓名 */
    @Excel(name = "姓名")
    @NotBlank(message = "姓名不能为空")
    @Size(max = 50, message = "姓名长度不能超过50个字符")
    private String studentName;

    /** 身份证号 */
    @Excel(name = "身份证号")
    @Size(max = 18, message = "身份证号长度不能超过18个字符")
    private String idCard;

    /** 年龄 */
    @Excel(name = "年龄", cellType = ColumnType.NUMERIC)
    @NotNull(message = "年龄不能为空")
    private Integer age;

    /** 性别（0男 1女） */
    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    private String gender;

    /** 年级 */
    @Excel(name = "年级")
    @Size(max = 20, message = "年级长度不能超过20个字符")
    private String grade;

    /** 班级 */
    @Excel(name = "班级")
    @Size(max = 50, message = "班级长度不能超过50个字符")
    private String className;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    private String phone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;

    /** 住址 */
    @Excel(name = "住址")
    @Size(max = 200, message = "住址长度不能超过200个字符")
    private String address;

    /** 入学日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入学日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date enrollmentDate;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Student()
    {
    }

    public Student(String studentNo)
    {
        this.studentNo = studentNo;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getStudentNo()
    {
        return studentNo;
    }

    public void setStudentNo(String studentNo)
    {
        this.studentNo = studentNo;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public String getIdCard()
    {
        return idCard;
    }

    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Date getEnrollmentDate()
    {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate)
    {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("studentNo", getStudentNo())
            .append("studentName", getStudentName())
            .append("idCard", getIdCard())
            .append("age", getAge())
            .append("gender", getGender())
            .append("grade", getGrade())
            .append("className", getClassName())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("address", getAddress())
            .append("enrollmentDate", getEnrollmentDate())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
