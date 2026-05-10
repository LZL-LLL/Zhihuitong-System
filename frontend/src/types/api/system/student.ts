import type { PageDomain, BaseEntity } from "../common";

/** 学生信息配置分页查询参数 */
export interface StudentQueryParams extends PageDomain {
  /** 学号 */
  studentNo?: string;
  /** 姓名 */
  studentName?: string;
  /** 身份证号 */
  idCard?: string;
  /** 年龄 */
  age?: number;
  /** 性别（0男 1女） */
  gender?: string;
  /** 年级 */
  grade?: string;
  /** 班级 */
  className?: string;
  /** 联系电话 */
  phone?: string;
  /** 邮箱 */
  email?: string;
  /** 住址 */
  address?: string;
  /** 入学日期 */
  enrollmentDate?: string;
  /** 状态（0正常 1停用） */
  status?: string;
}

/** 学生信息配置信息 */
export interface Student extends BaseEntity {
  /** 学号 */
  studentNo?: string;
  /** 姓名 */
  studentName?: string;
  /** 身份证号 */
  idCard?: string;
  /** 年龄 */
  age?: number;
  /** 性别（0男 1女） */
  gender?: string;
  /** 年级 */
  grade?: string;
  /** 班级 */
  className?: string;
  /** 联系电话 */
  phone?: string;
  /** 邮箱 */
  email?: string;
  /** 住址 */
  address?: string;
  /** 入学日期 */
  enrollmentDate?: string;
  /** 状态（0正常 1停用） */
  status?: string;
  /** 创建时间 */
  createTime?: string;
  /** 更新时间 */
  updateTime?: string;
}
