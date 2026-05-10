-- ----------------------------
-- 学生管理表（与sys_user关联，支持学生登录）
-- ----------------------------
DROP TABLE IF EXISTS `score`;
DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `student_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `user_id` BIGINT NOT NULL COMMENT '关联系统用户ID',
  `student_no` VARCHAR(50) NOT NULL COMMENT '学号',
  `student_name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `id_card` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
  `age` INT NOT NULL COMMENT '年龄',
  `gender` CHAR(1) DEFAULT '0' COMMENT '性别（0男 1女）',
  `grade` VARCHAR(20) DEFAULT NULL COMMENT '年级',
  `class_name` VARCHAR(50) DEFAULT NULL COMMENT '班级',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `address` VARCHAR(200) DEFAULT NULL COMMENT '住址',
  `enrollment_date` DATE DEFAULT NULL COMMENT '入学日期',
  `status` CHAR(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `uk_student_no` (`student_no`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  KEY `idx_student_name` (`student_name`),
  KEY `idx_grade` (`grade`),
  KEY `idx_class_name` (`class_name`),
  CONSTRAINT `fk_student_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生信息表';

-- ----------------------------
-- 成绩管理表
-- ----------------------------
CREATE TABLE `score` (
  `score_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
  `student_id` BIGINT NOT NULL COMMENT '学生ID',
  `course_name` VARCHAR(100) NOT NULL COMMENT '课程名称',
  `score_value` DECIMAL(5,2) NOT NULL COMMENT '成绩',
  `full_score` DECIMAL(5,2) DEFAULT '100.00' COMMENT '满分',
  `exam_type` CHAR(1) DEFAULT '0' COMMENT '考试类型（0期中考试 1期末考试 2月考 3平时成绩）',
  `exam_date` DATE DEFAULT NULL COMMENT '考试时间',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`score_id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_course_name` (`course_name`),
  KEY `idx_exam_type` (`exam_type`),
  CONSTRAINT `fk_score_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='成绩信息表';

-- ----------------------------
-- 插入学生用户（先创建系统用户，密码统一为123456）
-- ----------------------------
INSERT INTO `sys_user` (`user_name`, `nick_name`, `user_type`, `email`, `phonenumber`, `sex`, `password`, `status`, `del_flag`, `create_by`) VALUES
('student2024001', '张三', '02', 'zhangsan@example.com', '13800138001', '0', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', 'admin'),
('student2024002', '李四', '02', 'lisi@example.com', '13800138002', '1', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', 'admin'),
('student2024003', '王五', '02', 'wangwu@example.com', '13800138003', '0', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', 'admin'),
('student2024004', '赵六', '02', 'zhaoliu@example.com', '13800138004', '1', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', 'admin')
ON DUPLICATE KEY UPDATE 
  nick_name=VALUES(nick_name), 
  user_type=VALUES(user_type), 
  email=VALUES(email), 
  phonenumber=VALUES(phonenumber), 
  sex=VALUES(sex), 
  status=VALUES(status);

-- ----------------------------
-- 插入初始学生数据（通过用户名反查用户ID）
-- ----------------------------
INSERT INTO `student` (`user_id`, `student_no`, `student_name`, `id_card`, `age`, `gender`, `grade`, `class_name`, `phone`, `email`, `address`, `enrollment_date`, `status`)
SELECT u.user_id, '2024001', '张三', '110101199901011234', 18, '0', '高三', '一班', '13800138001', 'zhangsan@example.com', '北京市朝阳区', '2021-09-01', '0' FROM sys_user u WHERE u.user_name = 'student2024001';
INSERT INTO `student` (`user_id`, `student_no`, `student_name`, `id_card`, `age`, `gender`, `grade`, `class_name`, `phone`, `email`, `address`, `enrollment_date`, `status`)
SELECT u.user_id, '2024002', '李四', '110102199902022345', 17, '1', '高三', '一班', '13800138002', 'lisi@example.com', '北京市海淀区', '2021-09-01', '0' FROM sys_user u WHERE u.user_name = 'student2024002';
INSERT INTO `student` (`user_id`, `student_no`, `student_name`, `id_card`, `age`, `gender`, `grade`, `class_name`, `phone`, `email`, `address`, `enrollment_date`, `status`)
SELECT u.user_id, '2024003', '王五', '110103199903033456', 18, '0', '高三', '二班', '13800138003', 'wangwu@example.com', '北京市西城区', '2021-09-01', '0' FROM sys_user u WHERE u.user_name = 'student2024003';
INSERT INTO `student` (`user_id`, `student_no`, `student_name`, `id_card`, `age`, `gender`, `grade`, `class_name`, `phone`, `email`, `address`, `enrollment_date`, `status`)
SELECT u.user_id, '2024004', '赵六', '110104199904044567', 17, '1', '高三', '二班', '13800138004', 'zhaoliu@example.com', '北京市东城区', '2021-09-01', '0' FROM sys_user u WHERE u.user_name = 'student2024004';

-- ----------------------------
-- 插入初始成绩数据（通过学号反查学生ID）
-- ----------------------------
INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '语文', 92.50, 100.00, '1', '2024-01-15', '期末考试' FROM student s WHERE s.student_no = '2024001';
INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '数学', 88.00, 100.00, '1', '2024-01-16', '期末考试' FROM student s WHERE s.student_no = '2024001';
INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '英语', 95.00, 100.00, '1', '2024-01-17', '期末考试' FROM student s WHERE s.student_no = '2024001';

INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '语文', 85.00, 100.00, '1', '2024-01-15', '期末考试' FROM student s WHERE s.student_no = '2024002';
INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '数学', 92.00, 100.00, '1', '2024-01-16', '期末考试' FROM student s WHERE s.student_no = '2024002';
INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '英语', 88.50, 100.00, '1', '2024-01-17', '期末考试' FROM student s WHERE s.student_no = '2024002';

INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '语文', 90.00, 100.00, '1', '2024-01-15', '期末考试' FROM student s WHERE s.student_no = '2024003';
INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '数学', 95.00, 100.00, '1', '2024-01-16', '期末考试' FROM student s WHERE s.student_no = '2024003';
INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '英语', 91.00, 100.00, '1', '2024-01-17', '期末考试' FROM student s WHERE s.student_no = '2024003';

INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '语文', 87.00, 100.00, '1', '2024-01-15', '期末考试' FROM student s WHERE s.student_no = '2024004';
INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '数学', 89.50, 100.00, '1', '2024-01-16', '期末考试' FROM student s WHERE s.student_no = '2024004';
INSERT INTO `score` (`student_id`, `course_name`, `score_value`, `full_score`, `exam_type`, `exam_date`, `remark`)
SELECT s.student_id, '英语', 93.00, 100.00, '1', '2024-01-17', '期末考试' FROM student s WHERE s.student_no = '2024004';

-- ----------------------------
-- 为学生用户分配学生角色（假设学生角色ID为4）
-- ----------------------------
INSERT INTO `sys_user_role` (`user_id`, `role_id`)
SELECT u.user_id, 4 FROM sys_user u WHERE u.user_name LIKE 'student%' AND u.user_type = '02'
ON DUPLICATE KEY UPDATE role_id=VALUES(role_id);
