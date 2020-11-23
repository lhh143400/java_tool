<template>
    <div class="app-container">
        <el-row>
            <el-col :span="5">
                <div id="areaTree" class="top">
                    <div class="box-title">
                        <a href="#">
                            <svg-icon icon-class="permodule-icon" class="icons"></svg-icon>
                            权限模块列表</a>
                        <el-button type="primary" icon="el-icon-plus" @click="handleAddAclModule" circle
                                   size="mini"></el-button>
                    </div>
                    <div class="tree-box">
                        <div class="zTreeDemoBackground left" v-loading="ztreeLoading">
                            <ul id="treeDemo" class="ztree" style="padding: 10px;"></ul>
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
                                    <svg-icon icon-class="permission-dian" class="icons"></svg-icon>
                                    权限点列表<span
                                    style="font-size: 14px;margin-left: 8px;">{{permissionText}}</span></a>
                                <el-button type="primary" icon="el-icon-plus" @click="handleAddAcl" circle
                                           size="mini"></el-button>
                            </div>
                            <div style="padding: 15px;">
                                <span style="display: inline">
                                <el-input v-model="param" placeholder="请输入权限名称" style="width: 180px"
                                          size="small" @keyup.enter.native="handleSearch"></el-input>
                                </span>
                                <span style="margin-left: 5px">
                                <el-button type="primary" @click="handleSearch" icon="el-icon-search"
                                           size="small">查 询</el-button>
                            </span>
                            </div>
                            <el-table
                                :data="list"
                                border
                                stripe
                                tooltip-effect="dark"
                                v-loading.body="listLoading"
                                element-loading-text="Loading"
                                style="width: 100%;border-left:none;margin: 0 1px;">
                                <el-table-column
                                    type="index"
                                    :index="indexMethod"
                                    label="序号"
                                    align="center"
                                    width="60">
                                </el-table-column>
                                <el-table-column
                                    label="权限名称"
                                    align="center">
                                    <template slot-scope="scope">
                                        <span>{{scope.row.name}}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                    label="类型"
                                    align="center">
                                    <template slot-scope="scope">
                                        <span v-if="scope.row.type === 1"><svg-icon icon-class="menu-type"></svg-icon>菜单</span>
                                        <span v-else-if="scope.row.type === 2"><svg-icon
                                            icon-class="btn-type"></svg-icon>按钮</span>
                                        <span v-else><svg-icon icon-class="others-type"></svg-icon>其他</span>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                    label="URL"
                                    align="center">
                                    <template slot-scope="scope">
                                        <span>{{scope.row.url}}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                    label="状态"
                                    align="center">
                                    <template slot-scope="scope">
                                        <span v-if="scope.row.status === 1">正常</span>
                                        <span v-else>冻结</span>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                    label="顺序"
                                    align="center">
                                    <template slot-scope="scope">
                                        <span>{{scope.row.seq}}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                    label="操作"
                                    align="center"
                                    width="200">
                                    <template slot-scope="scope">
                                        <el-button
                                            size="mini"
                                            type="primary"
                                            icon="el-icon-edit"
                                            :disabled="scope.row.code === 'default'"
                                            @click="handleEdit(scope.row)">编 辑
                                        </el-button>
                                        <el-button
                                            size="mini"
                                            type="danger"
                                            :disabled="scope.row.code === 'default'"
                                            icon="el-icon-circle-close-outline"
                                            @click="handleDelete(scope.row)">删 除
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                            <div class="page-selection" style="margin: 10px 0px; float: right;">
                                <el-pagination
                                    background
                                    @current-change="handleCurrentChange"
                                    :current-page.sync="currentPage"
                                    :page-size="10"
                                    layout="total, prev, pager, next, jumper"
                                    :total="totalNumber">
                                </el-pagination>
                            </div>
                        </div>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>

        <!--对话框-->
        <el-dialog :visible.sync="dialogVisible" center width="50%"
                   show-close
                   style="font-weight: bold;">
            <template slot="title">
                <svg-icon icon-class='add'></svg-icon>
                <span style="font-weight: bold;">{{dialogTitle}}</span>
            </template>

            <el-form ref="sysAcl" :model="sysAcl" label-position="center" :rules="rules">
                <el-form-item label="所属权限 " label-width="90px" prop="selectedOptions"
                              class="form-item">
                    <el-cascader :options="options" filterable change-on-select v-model="sysAcl.selectedOptions"
                                 :props="props" style="width: 100%;" placeholder="请选择所属权限"></el-cascader>
                </el-form-item>
                <el-form-item label="名称 " label-width="90px" prop="name"
                              class="form-item">
                    <el-input type="text" v-model="sysAcl.name" auto-complete="off"
                              suffix-icon="el-icon-document" placeholder="请输入名称"></el-input>
                </el-form-item>
                <el-form-item label="类型" label-width="90px" prop="type"
                              class="form-item">
                    <el-select v-model="sysAcl.type" placeholder="请选择类型" style="width: 100%">
                        <el-option
                            v-for="item in typeOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                            <svg-icon :icon-class="item.iconCls"></svg-icon>
                            <span>{{item.label}}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="URL " label-width="90px" prop="url"
                              class="form-item">
                    <el-input type="text" v-model="sysAcl.url" auto-complete="off"
                              suffix-icon="el-icon-document" placeholder="请输入url"></el-input>
                </el-form-item>
                <el-form-item label="状态" label-width="90px" prop="status"
                              class="form-item">
                    <el-select v-model="sysAcl.status" placeholder="请选择状态" style="width: 100%">
                        <el-option
                            v-for="item in statusOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                            <svg-icon icon-class="role"></svg-icon>
                            <span>{{item.label}}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="顺序 " label-width="90px" prop="seq"
                              class="form-item">
                    <el-input type="text" v-model="sysAcl.seq" auto-complete="off"
                              suffix-icon="el-icon-message" placeholder="请输入顺序"></el-input>
                </el-form-item>
                <el-form-item label="备注 " label-width="90px" prop="remark"
                              class="form-item">
                    <el-input type="textarea" v-model="sysAcl.remark" auto-complete="off"
                              :autosize="{ minRows: 3, maxRows: 6}"
                              suffix-icon="el-icon-mobile-document" placeholder="请输入备注"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false" type="danger" icon="el-icon-error"> 取 消</el-button>
                <el-button type="primary" @click="submitForm('sysAcl','1')" v-show="showConfirm" icon="el-icon-success">
                    确
                    定
                </el-button>
                <el-button type="primary" @click="submitForm('sysAcl','2')" v-show="showAlter" icon="el-icon-success"> 修
                    改
                </el-button>
            </div>
        </el-dialog>

        <!--对话框2-->
        <el-dialog :visible.sync="dialogVisible2" center width="50%"
                   show-close
                   style="font-weight: bold;">
            <template slot="title">
                <svg-icon icon-class='add'></svg-icon>
                <span style="font-weight: bold;">{{dialogTitle2}}</span>
            </template>

            <el-form ref="aclModule" :model="aclModule" label-position="center" :rules="rules2">
                <el-form-item label="上级模块 " label-width="90px" prop="selectedOptions"
                              class="form-item">
                    <el-cascader :options="options" filterable change-on-select v-model="aclModule.selectedOptions"
                                 :props="props" style="width: 100%;" placeholder="请选择上级模块"></el-cascader>
                </el-form-item>
                <el-form-item label="名称 " label-width="90px" prop="name"
                              class="form-item">
                    <el-input type="text" v-model="aclModule.name" auto-complete="off"
                              suffix-icon="el-icon-document" placeholder="请输入名称"></el-input>
                </el-form-item>
                <el-form-item label="URL " label-width="90px" prop="url"
                              class="form-item">
                    <el-input type="text" v-model="aclModule.url" auto-complete="off"
                              suffix-icon="el-icon-document" placeholder="请确认URL"></el-input>
                </el-form-item>
                <el-form-item label="顺序 " label-width="90px" prop="order"
                              class="form-item">
                    <el-input type="number" v-model="aclModule.order" auto-complete="off"
                              suffix-icon="el-icon-document" placeholder="请输入顺序"></el-input>
                </el-form-item>
                <el-form-item label="状态 " label-width="90px" prop="status"
                              class="form-item">
                    <el-select v-model="aclModule.status" placeholder="选择状态"
                               style="width: 100%;">
                        <el-option
                            v-for="item in statusOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注 " label-width="90px" prop="remark"
                              class="form-item">
                    <el-input type="textarea" v-model="aclModule.remark" auto-complete="off"
                              :autosize="{ minRows: 3, maxRows: 6}"
                              suffix-icon="el-icon-mobile-document" placeholder="请输入备注"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible2 = false" type="danger" icon="el-icon-error"> 取 消</el-button>
                <el-button type="primary" @click="submitForm2('aclModule','1')" v-show="showConfirm2"
                           icon="el-icon-success">
                    确
                    定
                </el-button>
                <el-button type="primary" @click="submitForm2('aclModule','2')" v-show="showAlter2"
                           icon="el-icon-success">
                    修
                    改
                </el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import '../../../../static/ztree/js/jquery.ztree.all-3.5.min'
    import {getUserName} from '@/utils/auth'
    import {formatDate} from '@/utils/date'
    import {
        getAclModuleTree, addAclModule, updateAclModule, deleteAclModule
        , findAclByPage, addAcl, deleteAcl, updateAcl
    } from '@/api/permission'

    export default {
        name: 'Permission',
        data() {
            return {
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
                permissionText: '',
                ztreeLoading: true,
                typeOptions: [{value: 1, label: '菜单', iconCls: 'menu-type'},
                    {value: 2, label: '按钮', iconCls: 'btn-type'}, {value: 3, label: '其他', iconCls: 'others-type'}],
                statusOptions: [{value: 1, label: '有效'}, {value: 0, label: '冻结'}],
                sysAcl: {
                    'id': '',
                    'selectedOptions': [],
                    'name': '',
                    'url': '',
                    'type': 1,
                    'seq': null,
                    'status': 1,
                    'remark': ''
                },
                aclModule: {
                    'id': '',
                    'selectedOptions': [],
                    'name': '',
                    'url': '',
                    'order': null,
                    'status': 1,
                    'remark': ''
                },
                options: [],
                props: {
                    value: 'id',
                    label: 'name',
                    children: 'aclModuleList'
                },
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
                            children: 'aclModuleList'
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
                    selectedOptions: [
                        {required: true, message: '请选择所属模块', trigger: 'blur'}
                    ],
                    name: [
                        {required: true, message: '请输入名称', trigger: 'blur'}
                    ],
                    url: [
                        {required: true, message: '请输入url', trigger: 'blur'}
                    ],
                    type: [
                        {required: true, message: '请选择类型', trigger: 'blur'}
                    ],
                    seq: [
                        {required: true, message: '请输入顺序', trigger: 'blur'}
                    ],
                    status: [
                        {required: true, message: '请选择状态', trigger: 'blur'}
                    ],
                },
                rules2: {
                    selectedOptions: [
                        {required: true, message: '请选择上级模块', trigger: 'blur'}
                    ],
                    name: [
                        {required: true, message: '请输入名称', trigger: 'blur'}
                    ],
                    url: [
                        {required: true, message: '请输入url', trigger: 'blur'}
                    ],
                    order: [
                        {required: true, message: '请输入顺序', trigger: 'blur'}
                    ],
                    status: [
                        {required: true, message: '请选择状态', trigger: 'blur'}
                    ],
                }
            }
        },
        mounted() {
            this.getAclModuleData()
        },
        filters: {
            formatDate(time) {
                let date = new Date(time)
                return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
            }
        },
        methods: {
            // 获取权限模块列表
            getAclModuleData() {
                this.ztreeLoading = true
                getAclModuleTree().then(response => {
                    this.zNodes = response.data
                    this.zTree = $.fn.zTree.init($('#treeDemo'), this.setting, this.zNodes)
                    /*// 换图标
                    let treeObj = $.fn.zTree.getZTreeObj("treeDemo")
                    let nodes = treeObj.transformToArray(treeObj.getNodes())
                    for (let i = 0; i < nodes.length; i++) {
                        if (nodes[i].aclModuleList.length > 0) {
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
            // 获取权限点列表
            fetchData(currentPage, aclModuleId, keyword) {
                this.listLoading = true
                findAclByPage(currentPage, aclModuleId, keyword).then(response => {
                    this.list = response.data.list
                    this.totalNumber = response.data.totalNum
                    this.listLoading = false
                })
            },
            // 点击查询
            handleSearch() {
                this.fetchData(this.currentPage, this.sysAcl.id, this.param)
            },
            // 新增权限点
            handleAddAcl() {
                this.dialogTitle = '新增权限'
                this.dialogVisible = true
                this.showConfirm = true
                this.showAlter = false
                this.clearForm()
                this.getUpAcl()
            },
            // 编辑权限点
            handleEdit(row) {
                console.log(row)
                this.dialogTitle = '修改权限'
                this.dialogVisible = true
                this.showConfirm = false
                this.showAlter = true
                this.clearForm()
                this.getUpAcl()
                this.sysAcl.id = row.id
                this.sysAcl.name = row.name
                this.sysAcl.aclModuleId = row.aclModuleId
                if (row.fullLevel === '0') {
                    this.sysAcl.selectedOptions = [row.aclModuleId]
                } else {
                    this.sysAcl.selectedOptions = [row.fullLevel.split('.')[1, row.fullLevel.split('.').length - 1]].concat(row.aclModuleId)
                }
                this.sysAcl.url = row.url
                this.sysAcl.type = row.type
                this.sysAcl.status = row.status
                this.sysAcl.seq = row.seq
                this.sysAcl.remark = row.remark

            },
            // 删除权限点
            handleDelete(row) {
                this.$confirm('是否删除 【' + row.name + '】?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'info',
                    center: true
                }).then(() => {
                    deleteAcl(row.id).then(response => {
                        this.$notify.success({
                            title: '操作提示',
                            message: '删除成功'
                        })
                        this.fetchData(1, row.aclModuleId)
                    })
                })
            },
            // 新增权限模块
            handleAddAclModule() {
                this.dialogTitle2 = '新增权限模块'
                this.dialogVisible2 = true
                this.showConfirm2 = true
                this.showAlter2 = false
                this.clearForm2()
                this.getUpModule()
            },
            // 获取上级模块AclModule
            getUpModule() {
                this.aclModule.selectedOptions = []
                getAclModuleTree().then(response => {
                    this.options = this.getTreeData(response.data)
                    this.options.unshift({id: '0', name: '根目录'});
                })
            },
            // 递归方法
            getTreeData(data) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].aclModuleList.length < 1) {
                        data[i].aclModuleList = undefined;
                    } else {
                        this.getTreeData(data[i].aclModuleList);
                    }
                }
                return data;
            },
            // 获取上级模块Acl
            getUpAcl() {
                this.sysAcl.selectedOptions = []
                getAclModuleTree().then(response => {
                    this.options = this.getTreeData(response.data)
                })
            },
            // 对话框1提交
            submitForm(form, type) {
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        if (type === '1') {
                            addAcl(this.sysAcl).then(response => {
                                this.$notify.success({
                                    title: '操作提示',
                                    message: response.message
                                })
                                this.dialogVisible = false
                                this.fetchData(1, this.sysAcl.selectedOptions[this.sysAcl.selectedOptions.length - 1])
                            })
                        } else if (type === '2') {
                            updateAcl(this.sysAcl.id, this.sysAcl).then(response => {
                                this.$notify.success({
                                    title: '操作提示',
                                    message: response.message
                                })
                                this.dialogVisible = false
                                this.fetchData(1, this.sysAcl.selectedOptions[this.sysAcl.selectedOptions.length - 1])
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
                            addAclModule(this.aclModule).then(response => {
                                this.$notify.success({
                                    title: '操作提示',
                                    message: response.message
                                })
                                this.dialogVisible2 = false
                                this.getAclModuleData()
                            })
                        } else if (type === '2') {
                            updateAclModule(this.aclModule.id, this.aclModule).then(response => {
                                this.$notify.success({
                                    title: '操作提示',
                                    message: response.message
                                })
                                this.dialogVisible2 = false
                                this.getAclModuleData()
                            })
                        }
                    } else {
                        console.log('validate .... false')
                    }
                })
            },
            clearForm() {
                this.sysAcl.id = ''
                this.sysAcl.name = ''
                this.sysAcl.selectedOptions = []
                this.sysAcl.aclModuleId = ''
                this.sysAcl.url = ''
                this.sysAcl.type = 1
                this.sysAcl.status = 1
                this.sysAcl.seq = null
                this.sysAcl.remark = ''
                //this.$refs['sysUser'].resetFields()
            },
            clearForm2() {
                this.aclModule.id = ''
                this.aclModule.name = ''
                this.aclModule.url = ''
                this.aclModule.order = null
                this.aclModule.status = 1
                this.aclModule.remark = ''
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
                this.permissionText = '(' + treeNode.name + ')'
                this.sysAcl.id = treeNode.id
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
                    deleteAclModule(treeNode.id).then(response => {
                        this.$notify.success({
                            title: '操作提示',
                            message: '删除成功'
                        })
                        this.getAclModuleData()
                        return true
                    })
                }).catch(() => {
                    return false
                })
                return false
            },
            beforeEditName(treeId, treeNode) {
                this.dialogTitle2 = '修改权限模块'
                this.dialogVisible2 = true
                this.showConfirm2 = false
                this.showAlter2 = true
                this.clearForm2()
                this.getUpModule()
                this.aclModule.id = treeNode.id
                if (treeNode.parentId === '0') {
                    this.aclModule.selectedOptions = ['0']
                } else {
                    this.aclModule.selectedOptions = [treeNode.fullLevel.split('.')[1, treeNode.fullLevel.split('.').length - 1]]
                }
                this.aclModule.name = treeNode.name
                this.aclModule.url = treeNode.moduleUrl
                this.aclModule.status = treeNode.status
                this.aclModule.order = treeNode.seq
                this.aclModule.remark = treeNode.remark
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
        font-size: 1.2em;
    }
</style>
