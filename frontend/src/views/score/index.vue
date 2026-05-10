<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="studentNo">
        <el-input
          v-model="queryParams.studentNo"
          placeholder="请输入学号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程名称" prop="courseName">
        <el-input
          v-model="queryParams.courseName"
          placeholder="请输入课程名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="成绩" prop="scoreValue">
        <el-input
          v-model="queryParams.scoreValue"
          placeholder="请输入成绩"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="满分" prop="fullScore">
        <el-input
          v-model="queryParams.fullScore"
          placeholder="请输入满分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班级" prop="className">
        <el-input
          v-model="queryParams.className"
          placeholder="请输入班级"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年级" prop="grade">
        <el-input
          v-model="queryParams.grade"
          placeholder="请输入年级"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考试时间" prop="examDate">
        <el-date-picker clearable
          v-model="queryParams.examDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择考试时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['student:score:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['student:score:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['student:score:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['student:score:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="scoreList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="成绩ID" align="center" prop="scoreId" />
      <el-table-column label="学号" align="center" prop="studentNo" width="120" />
      <el-table-column label="姓名" align="center" prop="studentName" />
      <el-table-column label="班级" align="center" prop="className" />
      <el-table-column label="年级" align="center" prop="grade" />
      <el-table-column label="课程名称" align="center" prop="courseName" />
      <el-table-column label="成绩" align="center" prop="scoreValue" />
      <el-table-column label="满分" align="center" prop="fullScore" />
      <el-table-column label="考试类型" align="center" prop="examType" />
      <el-table-column label="考试时间" align="center" prop="examDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.examDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['student:score:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['student:score:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改成绩信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="scoreRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="学号" prop="studentNo">
              <el-input v-model="form.studentNo" placeholder="请输入学号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="课程名称" prop="courseName">
              <el-input v-model="form.courseName" placeholder="请输入课程名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="成绩" prop="scoreValue">
              <el-input v-model="form.scoreValue" placeholder="请输入成绩" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="满分" prop="fullScore">
              <el-input v-model="form.fullScore" placeholder="请输入满分" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="考试类型" prop="examType">
              <el-select v-model="form.examType" placeholder="请选择考试类型">
                <el-option label="期中考试" value="0" />
                <el-option label="期末考试" value="1" />
                <el-option label="月考" value="2" />
                <el-option label="平时成绩" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="考试时间" prop="examDate">
              <el-date-picker clearable
                v-model="form.examDate"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择考试时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="Score">
import type { Score, ScoreQueryParams } from "@/types/api/system/score"
import { listScore, getScore, delScore, addScore, updateScore } from "@/api/system/score"

const { proxy } = getCurrentInstance()

const scoreList = ref<Score[]>([])
const open = ref<boolean>(false)
const loading = ref<boolean>(true)
const showSearch = ref<boolean>(true)
const ids = ref<number[]>([])
const single = ref<boolean>(true)
const multiple = ref<boolean>(true)
const total = ref<number>(0)
const title = ref<string>("")

const data = reactive({
  form: {} as Score,
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    studentNo: undefined,
    courseName: undefined,
    scoreValue: undefined,
    fullScore: undefined,
    examType: undefined,
    examDate: undefined,
    className: undefined,
    grade: undefined,
  } as ScoreQueryParams,
  rules: {
    studentNo: [
      { required: true, message: "学号不能为空", trigger: "blur" }
    ],
    courseName: [
      { required: true, message: "课程名称不能为空", trigger: "blur" }
    ],
    scoreValue: [
      { required: true, message: "成绩不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询成绩信息列表 */
function getList() {
  loading.value = true
  listScore(queryParams.value).then(response => {
    scoreList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 取消按钮 */
function cancel() {
  open.value = false
  reset()
}

/** 表单重置 */
function reset() {
  form.value = {
    scoreId: null,
    studentNo: null,
    courseName: null,
    scoreValue: null,
    fullScore: null,
    examType: null,
    examDate: null,
    remark: null,
    createTime: null,
    updateTime: null
  }
  proxy.resetForm("scoreRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 多选框选中数据 */
function handleSelectionChange(selection: Score[]) {
  ids.value = selection.map(item => item.scoreId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加成绩信息"
}

/** 修改按钮操作 */
function handleUpdate(row: Score) {
  reset()
  const _scoreId = row.scoreId || ids.value[0]
  getScore(_scoreId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改成绩信息"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["scoreRef"].validate((valid: boolean) => {
    if (valid) {
      if (form.value.scoreId != null) {
        updateScore(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addScore(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row: Score) {
  const _scoreIds = row.scoreId || ids.value
  proxy.$modal.confirm('是否确认删除成绩编号为"' + _scoreIds + '"的数据项？').then(function() {
    return delScore(_scoreIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('student/score/export', {
    ...queryParams.value
  }, `score_${new Date().getTime()}.xlsx`)
}

getList()
</script>
