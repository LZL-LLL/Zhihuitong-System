import request from '@/utils/request'
import type { AjaxResult, TableDataInfo, ScoreQueryParams, Score } from '@/types'

// 查询成绩信息列表
export function listScore(query: ScoreQueryParams): Promise<TableDataInfo<Score[]>> {
  return request({
    url: '/student/score/list',
    method: 'get',
    params: query
  })
}

// 查询成绩信息详细
export function getScore(scoreId: number): Promise<AjaxResult<Score>> {
  return request({
    url: '/student/score/' + scoreId,
    method: 'get'
  })
}

// 通过学号查询成绩列表
export function getScoreByStudent(studentNo: string): Promise<AjaxResult<Score[]>> {
  return request({
    url: '/student/score/student/' + studentNo,
    method: 'get'
  })
}

// 新增成绩信息
export function addScore(data: Score): Promise<AjaxResult> {
  return request({
    url: '/student/score',
    method: 'post',
    data: data
  })
}

// 修改成绩信息
export function updateScore(data: Score): Promise<AjaxResult> {
  return request({
    url: '/student/score',
    method: 'put',
    data: data
  })
}

// 删除成绩信息
export function delScore(scoreId: number | number[]): Promise<AjaxResult> {
  return request({
    url: '/student/score/' + scoreId,
    method: 'delete'
  })
}

// 导出成绩信息
export function exportScore(query: ScoreQueryParams): Promise<void> {
  return request({
    url: '/student/score/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

// 导入成绩数据
export function importScore(data: FormData): Promise<AjaxResult> {
  return request({
    url: '/student/score/importData',
    method: 'post',
    data: data
  })
}

// 下载导入模板
export function importScoreTemplate(): Promise<void> {
  return request({
    url: '/student/score/importTemplate',
    method: 'post',
    responseType: 'blob'
  })
}
