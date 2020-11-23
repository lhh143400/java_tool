<template>
  <div class="app-container">
    <el-row>
      <el-col :span="5">
        <div id="areaTree" class="top">
          <div class="box-title">
            <a href="#">
              <svg-icon icon-class="dept-list" class="icons"/>
              部门列表</a>
            <el-button
              :disabled="addDeptBtn"
              type="primary"
              icon="el-icon-plus"
              circle
              size="mini"
              @click="handleAddDept"/>
          </div>
          <div class="tree-box">
            <div v-loading="ztreeLoading" class="zTreeDemoBackground left">
              <ul id="treeDemo" class="ztree" style="padding: 10px;"/>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="19" style="padding-left: 20px;">

        <el-row>
          <el-col :span="24">
            <div class="top">
              <div class="box-title">
                <a href="#">
                  <svg-icon icon-class="user-list" class="icons"/>
                  用户列表<span
                    style="font-size: 14px;margin-left: 8px;">({{ deptText }})</span></a>
                <el-button
                  :disabled="addUserBtn"
                  type="primary"
                  icon="el-icon-plus"
                  circle
                  size="mini"
                  @click="handleAddUser"/>
              </div>
              <div style="padding: 15px;">
                <el-select v-model="type" placeholder="请选择类型" style="width: 130px" size="small">
                  <el-option
                    v-for="item in typeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    <svg-icon :icon-class="item.iconCls"/>
                    <span>{{ item.label }}</span>
                  </el-option>
                </el-select>
                <span style="display: inline">
                  <el-input
                    v-if="this.type===1"
                    v-model="param"
                    placeholder="请输入用户名称"
                    style="width: 180px"
                    size="small"/>
                  <el-input
                    v-else
                    v-model="param"
                    placeholder="请输入角色名称"
                    style="width: 180px"
                    size="small"/>
                </span>
                <span style="margin-left: 5px">
                  <el-button
                    type="primary"
                    icon="el-icon-search"
                    size="small"
                    @click="handleSearch">查 询</el-button>
                </span>
                <span style="margin-left: 5px">
                  <el-button
                    type="info"
                    icon="el-icon-refresh"
                    size="small"
                    @click="handleReset">重 置</el-button>
                </span>
              </div>
              <el-table
                v-loading.body="listLoading"
                :data="list"
                border
                stripe
                tooltip-effect="dark"
                element-loading-text="Loading"
                style="width: 100%;border-left:none;margin: 0 1px;">
                <el-table-column
                  :index="indexMethod"
                  type="index"
                  label="序号"
                  align="center"
                  width="60"/>
                <el-table-column
                  label="用户名称"
                  align="center">
                  <template slot-scope="scope">
                    <span>{{ scope.row.username }}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="角色信息"
                  align="center">
                  <template slot-scope="scope">
                    <el-tag v-for="role in scope.row.roles" :key="role" style="margin-bottom: 8px;">
                      {{ role.name }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column
                  label="状态"
                  width="80"
                  align="center">
                  <template slot-scope="scope">
                    <span v-if="scope.row.status === 1">正常</span>
                    <span v-else>冻结</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="邮箱"
                  align="center">
                  <template slot-scope="scope">
                    <!--<svg-icon icon-class="mail"></svg-icon>-->
                    <span>{{ scope.row.mail }}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="联系方式"
                  width="120"
                  align="center">
                  <template slot-scope="scope">
                    <!--<svg-icon icon-class="phone"></svg-icon>-->
                    <span>{{ scope.row.telephone }}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="备注"
                  align="center">
                  <template slot-scope="scope">
                    <!--<i class="el-icon-time"></i>-->
                    <span>{{ scope.row.remark }}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作"
                  align="center"
                  width="200">
                  <template slot-scope="scope">
                    <el-button
                      :disabled="updateUserBtn"
                      size="mini"
                      type="primary"
                      icon="el-icon-edit"
                      @click="handleEdit(scope.row)">编 辑
                    </el-button>
                    <el-button
                      :disabled="deleteUserBtn || scope.row.username ==='admin' || scope.row.username ==='system'"
                      size="mini"
                      type="danger"
                      icon="el-icon-circle-close-outline"
                      @click="handleDelete(scope.row)">删 除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div class="page-selection" style="margin: 10px 0px; float: right;">
                <el-pagination
                  :current-page.sync="currentPage"
                  :page-size="10"
                  :total="totalNumber"
                  background
                  layout="total, prev, pager, next, jumper"
                  @current-change="handleCurrentChange"/>
              </div>
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>

    <!--对话框-->
    <el-dialog
      :visible.sync="dialogVisible"
      center
      width="50%"
      show-close
      style="font-weight: bold;">
      <template slot="title">
        <svg-icon icon-class="add"/>
        <span style="font-weight: bold;">{{ dialogTitle }}</span>
      </template>

      <el-form ref="sysUser" :model="sysUser" :rules="rules" label-position="center">
        <el-form-item
          label="用户名称 "
          label-width="90px"
          prop="username"
          class="form-item">
          <el-input
            v-model="sysUser.username"
            type="text"
            auto-complete="off"
            suffix-icon="el-icon-document"
            placeholder="请输入用户名称"/>
        </el-form-item>
        <el-form-item
          label="所属部门 "
          label-width="90px"
          prop="deptId"
          class="form-item">
          <el-cascader
            :options="options"
            v-model="sysUser.deptId"
            :props="props"
            filterable
            change-on-select
            style="width: 100%;"
            placeholder="请选择所属部门"/>
        </el-form-item>
        <el-form-item
          label="所属角色"
          label-width="90px"
          prop="roleIds"
          class="form-item">
          <el-select v-model="sysUser.roleIds" multiple placeholder="请选择用户所属角色" style="width: 100%">
            <el-option
              v-for="item in roleIdsOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
              <svg-icon icon-class="role-select"/>
              <span>{{ item.label }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="状态"
          label-width="90px"
          prop="status"
          class="form-item">
          <el-select v-model="sysUser.status" placeholder="请选择状态" style="width: 100%">
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
              <svg-icon icon-class="role"/>
              <span>{{ item.label }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="邮箱 "
          label-width="90px"
          prop="mail"
          class="form-item">
          <el-input
            v-model="sysUser.mail"
            type="text"
            auto-complete="off"
            suffix-icon="el-icon-message"
            placeholder="请输入用户邮箱"/>
        </el-form-item>
        <el-form-item
          label="联系方式 "
          label-width="90px"
          prop="phone"
          class="form-item">
          <el-input
            v-model="sysUser.phone"
            type="text"
            auto-complete="off"
            suffix-icon="el-icon-mobile-phone"
            placeholder="请输入用户联系方式"/>
        </el-form-item>
        <el-form-item
          label="备注 "
          label-width="90px"
          prop="remark"
          class="form-item">
          <el-input
            v-model="sysUser.remark"
            :autosize="{ minRows: 3, maxRows: 6}"
            type="textarea"
            auto-complete="off"
            suffix-icon="el-icon-mobile-document"
            placeholder="请输入备注"/>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="danger" icon="el-icon-error" @click="dialogVisible = false"> 取 消</el-button>
        <el-button
          v-show="showConfirm"
          type="primary"
          icon="el-icon-success"
          @click="submitForm('sysUser','1')"> 确
          定
        </el-button>
        <el-button v-show="showAlter" type="primary" icon="el-icon-success" @click="submitForm('sysUser','2')">
          修
          改
        </el-button>
      </div>
    </el-dialog>

    <!--对话框2-->
    <el-dialog
      :visible.sync="dialogVisible2"
      center
      width="50%"
      show-close
      style="font-weight: bold;">
      <template slot="title">
        <svg-icon icon-class="add"/>
        <span style="font-weight: bold;">{{ dialogTitle2 }}</span>
      </template>

      <el-form ref="sysDept" :model="sysDept" :rules="rules2" label-position="center">
        <el-form-item
          label="上级部门 "
          label-width="90px"
          prop="selectedOptions"
          class="form-item">
          <el-cascader
            :options="options"
            v-model="sysDept.selectedOptions"
            :props="props"
            size="small"
            filterable
            change-on-select
            style="width: 100%;"
            placeholder="请选择上级部门"/>
        </el-form-item>
        <el-form-item
          label="名称 "
          label-width="90px"
          prop="name"
          class="form-item">
          <el-input
            v-model="sysDept.name"
            type="text"
            auto-complete="off"
            suffix-icon="el-icon-document"
            placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item
          label="顺序 "
          label-width="90px"
          prop="order"
          class="form-item">
          <el-input
            v-model="sysDept.order"
            type="number"
            auto-complete="off"
            suffix-icon="el-icon-document"
            placeholder="请输入顺序"/>
        </el-form-item>
        <el-form-item
          label="备注 "
          label-width="90px"
          prop="remark"
          class="form-item">
          <el-input
            v-model="sysDept.remark"
            :autosize="{ minRows: 3, maxRows: 6}"
            type="textarea"
            auto-complete="off"
            suffix-icon="el-icon-mobile-document"
            placeholder="请输入备注"/>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="danger" icon="el-icon-error" @click="dialogVisible2 = false"> 取 消</el-button>
        <el-button
          v-show="showConfirm2"
          type="primary"
          icon="el-icon-success"
          @click="submitForm2('sysDept','1')"> 确
          定
        </el-button>
        <el-button
          v-show="showAlter2"
          type="primary"
          icon="el-icon-success"
          @click="submitForm2('sysDept','2')"> 修
          改
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import '../../../../static/ztree/js/jquery.ztree.all-3.5.min'
import { getUserName } from '@/utils/auth'
import { formatDate } from '@/utils/date'
import {
  getDeptTree, addDept, updateDept, deleteDept, findUserByRoleName,
  findUserByPage, addUser, updateUser, deleteUser
} from '@/api/users'
import { getAllRoles } from '@/api/roles'
import ElIcon from '../../../../node_modules/element-ui/packages/icon/src/icon.vue'

export default {
  name: 'Users',
  components: { ElIcon },
  data() {
    var validatePhone = (rule, value, callback) => {
      const regex = /^[1][3,4,5,7,8][0-9]{9}$/
      if (value != '' && !regex.test(value)) {
        return callback(new Error('请输入正确的手机号码'))
      } else {
        return callback()
      }
    }
    return {
      addDeptBtn: false,
      updateDeptBtn: false,
      deleteDeptBtn: false,
      addUserBtn: false,
      updateUserBtn: false,
      deleteUserBtn: false,
      list: [],
      listLoading: false,
      param: '',
      currentPage: 1,
      totalNumber: 0,
      dialogTitle: '',
      dialogVisible: false,
      dialogTitle2: '',
      dialogVisible2: false,
      showAlter: false,
      showConfirm: false,
      showAlter2: false,
      showConfirm2: false,
      ztreeLoading: false,
      roleIdsOptions: [],
      deptIdsOptions: [],
      acls: '',
      type: 1,
      typeOptions: [{ value: 1, label: '用户名称', iconCls: 'user-icon' },
        { value: 2, label: '角色名称', iconCls: 'role-select' }],
      statusOptions: [{ value: 1, label: '正常' }, { value: 0, label: '冻结' }],
      sysUser: {
        'id': '',
        'username': '',
        'roleIds': [],
        'deptId': [],
        'status': 1,
        'mail': '',
        'phone': '',
        'remark': ''
      },
      sysDept: {
        'id': '',
        'selectedOptions': [],
        'name': '',
        'order': null,
        'remark': ''
      },
      options: [],
      props: {
        value: 'id',
        label: 'name',
        children: 'deptList'
      },
      deptText: '全部',
      zTree: null,
      setting: {
        view: {
          showLine: true
        },
        data: {
          simpleData: {
            enable: true
          },
          key: {
            name: 'name',
            children: 'deptList'
          }
        },
        edit: {
          enable: true,
          showRemoveBtn: true,
          showRenameBtn: true,
          removeTitle: '删除',
          renameTitle: '编辑'
        },
        callback: {
          beforeClick: this.beforeClick,
          onClick: this.zTreeOnClick,
          beforeRemove: this.beforeRemove,
          beforeEditName: this.beforeEditName
        }
      },
      zNodes: [],
      rules: {
        username: [
          { required: true, message: '请输入用户名称', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '请选择用户所属部门', trigger: 'blur' }
        ],
        roleIds: [
          { required: true, message: '请选择用户所属角色', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ],
        mail: [
          { required: true, type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        phone: [
          { required: true, validator: validatePhone, trigger: ['blur', 'change'] }
        ]
      },
      rules2: {
        selectedOptions: [
          { required: true, message: '请选择上级部门', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        order: [
          { required: true, message: '请输入顺序', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchData(this.currentPage)
    this.acls = localStorage.getItem('acls')
    this.addDeptBtn = this.acls.indexOf('sys/dept/save') === -1
    this.updateDeptBtn = this.acls.indexOf('sys/dept/update') === -1
    this.deleteDeptBtn = this.acls.indexOf('sys/dept/delete') === -1
    this.addUserBtn = this.acls.indexOf('sys/user/save') === -1
    this.updateUserBtn = this.acls.indexOf('sys/user/update') === -1
    this.deleteUserBtn = this.acls.indexOf('sys/user/delete') === -1
    this.setting.edit.showRemoveBtn = !this.deleteDeptBtn
    this.setting.edit.showRenameBtn = !this.updateDeptBtn
  },
  mounted() {
    this.getDeptTreeData()
  },
  methods: {
    // 获取部门列表
    getDeptTreeData() {
      this.ztreeLoading = true
      getDeptTree().then(response => {
        this.zNodes = response.data
        this.zTree = $.fn.zTree.init($('#treeDemo'), this.setting, this.zNodes)
        /* // 换图标
                    let treeObj = $.fn.zTree.getZTreeObj("treeDemo")
                    let nodes = treeObj.transformToArray(treeObj.getNodes())
                    for (let i = 0; i < nodes.length; i++) {
                        if (nodes[i].deptList.length > 0) {
                            nodes[i].iconOpen = "../../../../static/ztree/css/zTreeStyle/img/diy/parent-open.png"
                            nodes[i].iconClose = "../../../../static/ztree/css/zTreeStyle/img/diy/parent-close.png"
                        } else {
                            nodes[i].icon = "../../../../static/ztree/css/zTreeStyle/img/diy/children-node.png"
                        }
                    }*/
        this.zTree.expandAll(true)
        if (this.zNodes.length === 0) {
          $('#treeDemo').append(`<span style='margin-left: 5px;font-size:14px;color:#aaa;'>暂无数据...</span>`)
        }
        this.ztreeLoading = false
      })
    },
    // 获取用户列表
    fetchData(currentPage, deptId, keyword) {
      this.listLoading = true
      findUserByPage(currentPage, deptId, keyword).then(response => {
        this.list = response.data.list
        this.totalNumber = response.data.totalNum
        this.listLoading = false
      })
    },
    // 获取角色列表
    getRoles() {
      this.roleIdsOptions = []
      getAllRoles().then(response => {
        response.data.forEach((value, index, array) => {
          this.roleIdsOptions.push({ value: value.id, label: value.name })
        })
      })
    },
    // 获取部门列表
    getDept() {
      this.sysUser.deptId = []
      getDeptTree().then(response => {
        this.options = this.getTreeData(response.data)
      })
    },
    // 点击查询
    handleSearch() {
      if (this.type === 1) {
        this.fetchData(this.currentPage, this.sysUser.id, this.param)
      } else {
        this.listLoading = true
        findUserByRoleName(this.currentPage, this.sysUser.id, this.param).then(response => {
          this.list = response.data.list
          this.totalNumber = response.data.totalNum
          this.listLoading = false
        })
      }
    },
    // 点击重置
    handleReset() {
      this.type = 1
      this.param = ''
      this.sysUser.id = ''
      this.deptText = '全部'
      $.fn.zTree.getZTreeObj('treeDemo').cancelSelectedNode()
      this.fetchData(this.currentPage, this.sysUser.id, this.param)
    },
    // 新增用户
    handleAddUser() {
      this.clearForm()
      this.dialogTitle = '新增用户'
      this.dialogVisible = true
      this.showConfirm = true
      this.showAlter = false
      this.getRoles()
      this.getDept()
    },
    // 编辑用户
    handleEdit(row) {
      console.log(row)
      this.dialogTitle = '修改用户'
      this.dialogVisible = true
      this.showConfirm = false
      this.showAlter = true
      this.clearForm()
      this.getDept()
      this.sysUser.id = row.id
      this.sysUser.deptId = row.deptId
      this.sysUser.username = row.username
      this.sysUser.status = row.status
      this.sysUser.mail = row.mail
      this.sysUser.phone = row.telephone
      if (row.fullLevel === '0') {
        this.sysUser.deptId = [row.deptId]
      } else {
        this.sysUser.deptId = [row.fullLevel.split('.')[1, row.fullLevel.split('.').length - 1]].concat(row.deptId)
      }
      if (row.roleIds.indexOf(',')) {
        this.sysUser.roleIds = row.roleIds.substring(0, row.roleIds.length - 1).split(',')
      } else {
        this.sysUser.roleIds.push(row.roleIds.substring(0, row.roleIds.length - 1))
      }
      this.sysUser.remark = row.remark
      this.getRoles()
    },
    // 删除用户
    handleDelete(row) {
      this.$confirm('是否删除用户 【' + row.username + '】?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
        center: true
      }).then(() => {
        deleteUser(row.id).then(response => {
          this.$notify.success({
            title: '操作提示',
            message: '删除成功'
          })
          this.fetchData(1, row.deptId)
        })
      })
    },
    // 递归方法
    getTreeData(data) {
      for (var i = 0; i < data.length; i++) {
        if (data[i].deptList.length < 1) {
          data[i].deptList = undefined
        } else {
          this.getTreeData(data[i].deptList)
        }
      }
      return data
    },
    // 获取上级部门deptList
    getUpDept() {
      this.sysDept.selectedOptions = []
      getDeptTree().then(response => {
        this.options = this.getTreeData(response.data)
        this.options.unshift({ id: '0', name: '根目录' })
      })
    },
    // 新增部门
    handleAddDept() {
      this.dialogTitle2 = '新增部门'
      this.dialogVisible2 = true
      this.showConfirm2 = true
      this.showAlter2 = false
      this.clearForm2()
      this.getUpDept()
    },
    // 对话框1提交
    submitForm(form, type) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (this.sysUser.roleIds.length > 1) {
            this.sysUser.roleIds = this.sysUser.roleIds.join(',')
          } else {
            this.sysUser.roleIds = this.sysUser.roleIds[0]
          }
          if (type === '1') {
            addUser(this.sysUser).then(response => {
              this.$notify.success({
                title: '操作提示',
                message: response.message
              })
              this.dialogVisible = false
              this.fetchData(1, this.sysUser.deptId[this.sysUser.deptId.length - 1])
            })
          } else if (type === '2') {
            updateUser(this.sysUser.id, this.sysUser).then(response => {
              this.$notify.success({
                title: '操作提示',
                message: response.message
              })
              this.dialogVisible = false
              this.fetchData(1, this.sysUser.deptId[this.sysUser.deptId.length - 1])
            })
          }
        } else {
          console.log('validate .... false')
        }
      })
    },
    // 对话框2提交
    submitForm2(form, type) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (type === '1') {
            addDept(this.sysDept).then(response => {
              this.$notify.success({
                title: '操作提示',
                message: response.message
              })
              this.dialogVisible2 = false
              this.getDeptTreeData()
            })
          } else if (type === '2') {
            updateDept(this.sysDept.id, this.sysDept).then(response => {
              this.$notify.success({
                title: '操作提示',
                message: response.message
              })
              this.dialogVisible2 = false
              this.getDeptTreeData()
            })
          }
        } else {
          console.log('validate .... false')
        }
      })
    },
    clearForm() {
      this.sysUser.id = ''
      this.sysUser.username = ''
      this.sysUser.mail = ''
      this.sysUser.phone = ''
      this.sysUser.status = 1
      this.sysUser.deptId = []
      this.sysUser.roleIds = []
      this.sysUser.remark = ''
      // this.$refs['sysUser'].resetFields()
    },
    clearForm2() {
      this.sysDept.id = ''
      this.sysDept.name = ''
      this.sysDept.order = null
      this.sysDept.remark = ''
    },
    // 序号自增
    indexMethod(index) {
      return (this.currentPage - 1) * 10 + (index + 1)
    },
    // 翻页
    handleCurrentChange(val) {
      this.currentPage = parseInt(`${val}`)
      this.fetchData(this.currentPage)
    },
    // ztree
    zTreeOnClick(event, treeId, treeNode) {
      this.sysUser.id = treeNode.id
      this.deptText = treeNode.name
      this.fetchData(this.currentPage, treeNode.id)
    },
    beforeClick(treeId, treeNode) {
    },
    beforeRemove(treeId, treeNode) {
      this.$confirm('是否删除' + treeNode.name + '?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info',
        center: true
      }).then(() => {
        deleteDept(treeNode.id).then(response => {
          this.$notify.success({
            title: '操作提示',
            message: '删除成功'
          })
          this.getDeptTreeData()
          return true
        })
      }).catch(() => {
        return false
      })
      return false
    },
    beforeEditName(treeId, treeNode) {
      this.dialogTitle2 = '修改部门'
      this.dialogVisible2 = true
      this.showConfirm2 = false
      this.showAlter2 = true
      this.clearForm2()
      this.getUpDept()
      this.sysDept.id = treeNode.id
      if (treeNode.parentId === '0') {
        this.sysDept.selectedOptions = ['0']
      } else {
        this.sysDept.selectedOptions = [treeNode.fullLevel.split('.')[1, treeNode.fullLevel.split('.').length - 1]]
      }
      this.sysDept.name = treeNode.name
      this.sysDept.order = treeNode.seq
      this.sysDept.remark = treeNode.remark
    }
  }
}
</script>

<style scoped>

    .form-item {
        padding: 5px 100px 0px 70px;
    }

    .top {
        border: 1px solid #e5e5e5;
        border-radius: 4px;
        overflow: hidden;
    }

    .box-title {
        border-radius: 3px 3px 0 0;
        background-color: #F5F7FA;
        position: relative;
        font-weight: bold;
    }

    .box-title a {
        color: #2fa4e7;
        text-decoration: none;
        font-size: 16px;
        display: block;
        padding: 10px 15px;
        cursor: auto;
        border-bottom: 1px solid #e5e5e5;
    }

    .box-title button {
        position: absolute;
        top: 5px;
        right: 15px;
    }

    .icons {
        font-size: 1.3em;
    }
</style>
