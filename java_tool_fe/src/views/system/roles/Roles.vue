<template>
    <div class="app-container">
        <el-row>
            <el-col :span="14">
                <div id="areaTree" class="top">
                    <div class="box-title">
                        <a href="#">
                            <svg-icon icon-class="role-list" class="icons"></svg-icon>
                            角色列表</a>
                        <el-button type="primary" icon="el-icon-plus" @click="handleAddRole" circle
                                   size="mini" :disabled="addRoleBtn"></el-button>
                    </div>
                    <el-table
                        :data="list"
                        border
                        stripe
                        tooltip-effect="dark"
                        v-loading.body="listLoading"
                        element-loading-text="Loading"
                        style="width: 100%;border:none;margin: 0 1px;">
                        <el-table-column
                            label="角色名称"
                            align="center">
                            <template slot-scope="scope">
                                <span>{{scope.row.name}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="角色类型"
                            align="center">
                            <template slot-scope="scope">
                                <span v-if="scope.row.type === 1"><svg-icon icon-class="role-list"></svg-icon>管理员角色</span>
                                <span v-else><svg-icon icon-class="role-others"></svg-icon>其他角色</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="角色状态"
                            align="center"
                            width="100">
                            <template slot-scope="scope">
                                <span v-if="scope.row.status === 1">可用</span>
                                <span v-else>冻结</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="操作"
                            align="center"
                            width="300">
                            <template slot-scope="scope">
                                <el-button
                                    size="mini"
                                    type="primary"
                                    icon="el-icon-edit"
                                    :disabled="updateRoleBtn"
                                    @click="handleEdit(scope.row)">编 辑
                                </el-button>
                                <el-button
                                    size="mini"
                                    type="danger"
                                    icon="el-icon-circle-close-outline"
                                    :disabled="deleteRoleBtn"
                                    @click="handleDelete(scope.row)">删 除
                                </el-button>
                                <el-button
                                    size="mini"
                                    type="success"
                                    icon="el-icon-view"
                                    @click="handlePermission(scope.row)">权 限
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </el-col>
            <el-col :span="10" style="padding-left: 20px;">
                <el-row>
                    <el-col :span="24">
                        <div class="top">
                            <div class="box-title">
                                <a href="#">
                                    <svg-icon icon-class="role-and-permission3" class="icons"></svg-icon>
                                    角色与权限<span style="font-size: 14px;margin-left: 8px;">{{roleText}}</span>
                                </a>
                                <el-button type="primary" icon="el-icon-success" @click="handleSave"
                                           size="mini" :disabled="saveTreeBtn">保 存
                                </el-button>
                            </div>
                            <div class="tree-box">
                                <div class="zTreeDemoBackground left" v-loading="ztreeLoading">
                                    <ul id="treeDemo" class="ztree" style="padding: 10px;"></ul>
                                </div>
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

            <el-form ref="sysRoles" :model="sysRoles" label-position="center" :rules="rules">
                <el-form-item label="名称 " label-width="90px" prop="name"
                              class="form-item">
                    <el-input type="text" v-model="sysRoles.name" auto-complete="off"
                              suffix-icon="el-icon-document" placeholder="请输入名称"></el-input>
                </el-form-item>
                <el-form-item label="类型 " label-width="90px" prop="type"
                              class="form-item">
                    <el-select v-model="sysRoles.type" placeholder="请选择类型" style="width: 100%">
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
                <el-form-item label="状态 " label-width="90px" prop="status"
                              class="form-item">
                    <el-select v-model="sysRoles.status" placeholder="请选择状态" style="width: 100%">
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
                <el-form-item label="备注 " label-width="90px" prop="remark"
                              class="form-item">
                    <el-input type="textarea" v-model="sysRoles.remark" auto-complete="off"
                              :autosize="{ minRows: 3, maxRows: 6}"
                              suffix-icon="el-icon-mobile-document" placeholder="请输入备注"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false" type="danger" icon="el-icon-error"> 取 消</el-button>
                <el-button type="primary" @click="submitForm('sysRoles','1')" v-show="showConfirm"
                           icon="el-icon-success"> 确
                    定
                </el-button>
                <el-button type="primary" @click="submitForm('sysRoles','2')" v-show="showAlter" icon="el-icon-success">
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
    import {getAllRoles, addRole, updateRole, deleteRole, getRolesTree, changeAcls} from '@/api/roles'

    let zTreeObj = [];
    let modulePrefix = 'm_';
    let aclPrefix = 'a_';
    let nodeMap = {};

    function recursivePrepareTreeData(aclModuleList) {
        // prepare nodeMap
        if (aclModuleList && aclModuleList.length > 0) {
            $(aclModuleList).each(function (i, aclModule) {
                var hasChecked = false;
                if (aclModule.aclList && aclModule.aclList.length > 0) {
                    $(aclModule.aclList).each(function (i, acl) {
                        zTreeObj.push({
                            id: aclPrefix + acl.id,
                            pId: modulePrefix + acl.aclModuleId,
                            name: acl.name + ((acl.type == 1) ? '(菜单)' : ''),
                            chkDisabled: !acl.hasAcl,
                            checked: acl.checked,
                            dataId: acl.id
                        });
                        if (acl.checked) {
                            hasChecked = true;
                        }
                    });
                }
                if ((aclModule.aclModuleList && aclModule.aclModuleList.length > 0) ||
                    (aclModule.aclList && aclModule.aclList.length > 0)) {
                    nodeMap[modulePrefix + aclModule.id] = {
                        id: modulePrefix + aclModule.id,
                        pId: modulePrefix + aclModule.parentId,
                        name: aclModule.name,
                        open: hasChecked
                    };
                    var tempAclModule = nodeMap[modulePrefix + aclModule.id];
                    while (hasChecked && tempAclModule) {
                        if (tempAclModule) {
                            nodeMap[tempAclModule.id] = {
                                id: tempAclModule.id,
                                pId: tempAclModule.pId,
                                name: tempAclModule.name,
                                open: true
                            }
                        }
                        tempAclModule = nodeMap[tempAclModule.pId];
                    }
                }
                recursivePrepareTreeData(aclModule.aclModuleList);
            });
        }
    }

    export default {
        name: 'Roles',
        data() {
            return {
                addRoleBtn: false,
                updateRoleBtn: false,
                deleteRoleBtn: false,
                saveTreeBtn: false,
                list: [],
                listLoading: false,
                param: '',
                roleText: '',
                dialogTitle: '',
                dialogVisible: false,
                showAlter: false,
                showConfirm: false,
                ztreeLoading: false,
                roleId: null,
                typeOptions: [{value: 1, label: '管理员角色', iconCls: 'role-admin'},
                    {value: 2, label: '其他角色', iconCls: 'role-others'}],
                statusOptions: [{value: 1, label: '可用'}, {value: 0, label: '冻结'}],
                sysRoles: {
                    'id': '',
                    'name': '',
                    'type': 1,
                    'status': 1,
                    'remark': ''
                },
                zTree: null,
                setting: {
                    view: {
                        showLine: true
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    check: {
                        enable: true,
                        chkDisabledInherit: true,
                        chkboxType: {"Y": "ps", "N": "ps"}, //auto check 父节点 子节点
                        autoCheckTrigger: true
                    },
                    callback: {
                        beforeCheck: this.beforeCheck,
                    }
                },
                zNodes: [],
                rules: {
                    name: [
                        {required: true, message: '请输入用户名称', trigger: 'blur'}
                    ],
                    type: [
                        {required: true, message: '请选择类型', trigger: 'blur'}
                    ],
                    status: [
                        {required: true, message: '请选择状态', trigger: 'blur'}
                    ]
                }
            }
        },
        created() {
            this.fetchData()
            this.acls = localStorage.getItem('acls')
            this.addRoleBtn = this.acls.indexOf('sys/role/save') === -1
            this.updateRoleBtn = this.acls.indexOf('sys/role/update') === -1
            this.deleteRoleBtn = this.acls.indexOf('sys/role/delete') === -1
            this.saveTreeBtn = this.acls.indexOf('sys/role/changeAcls') === -1
        },
        mounted() {
            if (this.zNodes.length === 0) {
                $('#treeDemo').append(`<span style='margin-left: 5px;font-size:14px;color:#aaa;'>点击权限按钮进行查询...</span>`)
            }
        },
        filters: {
            formatDate(time) {
                let date = new Date(time)
                return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
            }
        },
        methods: {
            fetchData() {
                this.listLoading = true
                getAllRoles().then(response => {
                    this.list = response.data
                    this.totalNumber = response.data
                    this.listLoading = false
                })
            },
            // 新增角色
            handleAddRole() {
                this.clearForm()
                this.dialogTitle = '新增角色'
                this.dialogVisible = true
                this.showConfirm = true
                this.showAlter = false
            },
            // 编辑角色
            handleEdit(row) {
                this.clearForm()
                this.dialogTitle = '修改角色'
                this.dialogVisible = true
                this.showConfirm = false
                this.showAlter = true
                this.sysRoles.id = row.id
                this.sysRoles.name = row.name
                this.sysRoles.type = row.type
                this.sysRoles.status = row.status
                this.sysRoles.remark = row.remark
            },
            // 删除角色
            handleDelete(row) {
                this.$confirm('是否删除角色 【' + row.name + '】?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'info',
                    center: true
                }).then(() => {
                    deleteRole(row.id).then(response => {
                        this.$notify.success({
                            title: '操作提示',
                            message: '删除成功'
                        })
                        this.fetchData()
                    })
                })
            },
            // 角色权限
            handlePermission(row) {
                this.ztreeLoading = true
                this.roleId = row.id
                this.roleText = '(' + row.name + ')'
                getRolesTree(row.id).then(response => {
                    this.renderRoleTree(response.data)
                    this.ztreeLoading = false
                })
            },
            // 加载权限树
            renderRoleTree(aclModuleList) {
                zTreeObj = [];
                recursivePrepareTreeData(aclModuleList);
                for (var key in nodeMap) {
                    zTreeObj.push(nodeMap[key]);
                }
                $.fn.zTree.init($("#treeDemo"), this.setting, zTreeObj);
            },
            // 点击保存
            handleSave() {
                let treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                let nodes = treeObj.getCheckedNodes(true);
                let aclIds = [];
                nodes.forEach((value, index, array) => {
                    aclIds.push(value.id.substring(2, value.id.length));
                })
                changeAcls(this.roleId, aclIds.join(',')).then(response => {
                    this.$notify.success({
                        title: '操作提示',
                        message: response.message
                    })
                })
            },
            // 对话框提交
            submitForm(form, type) {
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        if (type === '1') {
                            addRole(this.sysRoles).then(response => {
                                this.$notify.success({
                                    title: '操作提示',
                                    message: response.message
                                })
                                this.dialogVisible = false
                                this.fetchData()
                            })
                        } else if (type === '2') {
                            updateRole(this.sysRoles.id, this.sysRoles).then(response => {
                                this.$notify.success({
                                    title: '操作提示',
                                    message: response.message
                                })
                                this.dialogVisible = false
                                this.fetchData()
                            })
                        }
                    } else {
                        console.log('validate .... false')
                    }
                })
            },
            clearForm() {
                this.sysRoles.name = ''
                this.sysRoles.type = 1
                this.sysRoles.status = 1
                this.sysRoles.remark = ''
                //this.$refs['sysUser'].resetFields()
            },
            // 序号自增
            indexMethod(index) {
                return (this.currentPage - 1) * 10 + (index + 1)
            },
            beforeCheck() {

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
