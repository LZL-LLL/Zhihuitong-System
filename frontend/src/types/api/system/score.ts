import type { PageDomain, BaseEntity } from "../common";

/** 成绩信息配置分页查询参数 */
export interface ScoreQueryParams extends PageDomain {
  /** 学号 */
  studentNo?: string;
  /** 课程名称 */
  courseName?: string;
  /** 成绩 */
  scoreValue?: string;
  /** 满分 */
  fullScore?: string;
  /** 考试类型（0期中考试 1期末考试 2月考 3平时成绩） */
  examType?: string;
  /** 考试时间 */
  examDate?: string;
  /** 班级 */
  className?: string;
  /** 年级 */
  grade?: string;
}

/** 成绩信息配置信息 */
export interface Score extends BaseEntity {
  /** 成绩ID */
  scoreId?: number;
  /** 学号 */
  studentNo?: string;
  /** 姓名 */
  studentName?: string;
  /** 班级 */
  className?: string;
  /** 年级 */
  grade?: string;
  /** 课程名称 */
  courseName?: string;
  /** 成绩 */
  scoreValue?: string;
  /** 满分 */
  fullScore?: string;
  /** 考试类型（0期中考试 1期末考试 2月考 3平时成绩） */
  examType?: string;
  /** 考试时间 */
  examDate?: string;
  /** 备注 */
  remark?: string;
  /** 创建时间 */
  createTime?: string;
  /** 更新时间 */
  updateTime?: string;
}
