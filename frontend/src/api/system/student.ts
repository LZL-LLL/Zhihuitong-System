import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, StudentQueryParams, Student } from '@/types'

// 查询学生信息列表
export function listStudent(query: StudentQueryParams): Promise<TableDataInfo<Student[]>> {
  return request({
    url: '/student/list',
    method: 'get',
    params: query
  })
}

// 查询学生信息详细
export function getStudent(studentNo: string): Promise<AjaxResult<Student>> {
  return request({
    url: '/student/' + studentNo,
    method: 'get'
  })
}

// 新增学生信息
export function addStudent(data: Student): Promise<AjaxResult> {
  return request({
    url: '/student',
    method: 'post',
    data: data
  })
}

// 修改学生信息
export function updateStudent(data: Student): Promise<AjaxResult> {
  return request({
    url: '/student',
    method: 'put',
    data: data
  })
}

// 删除学生信息
export function delStudent(studentNo: string | string[]): Promise<AjaxResult> {
  return request({
    url: '/student/' + studentNo,
    method: 'delete'
  })
}

// 导出学生信息
export function exportStudent(query: StudentQueryParams): Promise<void> {
  return request({
    url: '/student/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}
