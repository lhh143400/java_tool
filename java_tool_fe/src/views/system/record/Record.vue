<template>
    <div class="app-container">
        <el-row>
            <el-col :span="24">
                <div>
                    <div style="padding-bottom: 15px;">
                        <span style="display: inline; font-size: 15px; font-weight: bold;">搜索条件：
                                 <el-select v-model="type" placeholder="请选择类型" style="width: 120px"
                                            size="small">
                                    <el-option
                                        v-for="item in typeOptions"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                        <svg-icon :icon-class="item.iconCls"></svg-icon>
                                        <span>{{item.label}}</span>
                                    </el-option>
                                </el-select>
                                </span>
                        <span style="display: inline">
                                <el-input v-model="operator" placeholder="请输入操作者" style="width: 150px"
                                          size="small"></el-input>
                                </span>
                        <span style="display: inline">
                                <el-input v-model="oldValue" placeholder="请输入操作前的值" style="width: 150px"
                                          size="small"></el-input>
                                </span>
                        <span style="display: inline">
                                <el-input v-model="newValue" placeholder="请输入操作后的值" style="width: 150px"
                                          size="small"></el-input>
                                </span>
                        <span style="margin-left: 5px">
                                <el-button type="primary" @click="handleSearch" icon="el-icon-search"
                                           size="small">查 询</el-button>
                            </span>
                        <span style="margin-left: 5px">
                                <el-button type="info" @click="handleReset" icon="el-icon-refresh"
                                           size="small">重 置</el-button>
                            </span>
                    </div>
                    <el-table
                        :data="list"
                        border
                        stripe
                        tooltip-effect="dark"
                        v-loading.body="listLoading"
                        element-loading-text="Loading"
                        style="width: 100%;">
                        <el-table-column
                            type="index"
                            :index="indexMethod"
                            label="序号"
                            align="center"
                            width="60">
                        </el-table-column>
                        <el-table-column
                            label="操作者"
                            width="90"
                            align="center">
                            <template slot-scope="scope">
                                <span>{{scope.row.operator}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="类型"
                            width="80"
                            align="center">
                            <template slot-scope="scope">
                                <span v-if="scope.row.type === 1">部门</span>
                                <span v-else-if="scope.row.type === 2">用户</span>
                                <span v-else-if="scope.row.type === 3">权限模块</span>
                                <span v-else-if="scope.row.type === 4">权限</span>
                                <span v-else-if="scope.row.type === 5">角色</span>
                                <span v-else-if="scope.row.type === 6">角色用户关系</span>
                                <span v-else-if="scope.row.type === 7">角色权限关系</span>
                                <span v-else>全部</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="操作时间"
                            width="100"
                            align="center">
                            <template slot-scope="scope">
                                <span>{{scope.row.operateTime | formatDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="操作前的值">
                            <template slot-scope="scope">
                                <pre>{{scope.row.oldValue}}</pre>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="操作后的值">
                            <template slot-scope="scope">
                                <pre>{{scope.row.newValue}}</pre>
                            </template>
                        </el-table-column>
                        <el-table-column
                            label="操作"
                            align="center"
                            width="120">
                            <template slot-scope="scope">
                                <el-button
                                    size="mini"
                                    type="primary"
                                    icon="el-icon-refresh"
                                    v-if="scope.row.status === 1"
                                    :disabled="recoverBtn"
                                    @click="handleRecover(scope.row)">还 原
                                </el-button>
                                <el-button
                                    size="mini"
                                    type="info"
                                    icon="el-icon-refresh"
                                    v-else-if="scope.row.status === 2"
                                    :disabled="true"
                                    @click="handleRecover(scope.row)">已还原
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
    </div>
</template>

<script>
    import '../../../../static/ztree/js/jquery.ztree.all-3.5.min'
    import {getUserName} from '@/utils/auth'
    import {formatDate} from '@/utils/date'
    import {findLogByPage, recover} from '@/api/system'

    export default {
        name: 'Users',
        data() {
            return {
                recoverBtn: false,
                list: [],
                listLoading: false,
                param: '',
                currentPage: 1,
                totalNumber: 0,
                operator: '',
                oldValue: '',
                newValue: '',
                type: '',
                typeOptions: [{value: '', label: '全部', iconCls: 'all-icon'}, {value: 1, label: '部门', iconCls: 'dept'},
                    {value: 2, label: '用户', iconCls: 'user-icon2'},
                    {value: 3, label: '权限模块', iconCls: 'permission-module'},
                    {value: 4, label: '权限', iconCls: 'permission-dian'}, {value: 5, label: '角色', iconCls: 'role-icon'},
                    {value: 6, label: '角色用户关系', iconCls: 'role-user'},
                    {value: 7, label: '角色权限关系', iconCls: 'role-permission'}],
            }
        },
        created() {
            this.fetchData(1)
            this.acls = localStorage.getItem('acls')
            this.recoverBtn = this.acls.indexOf('sys/log/recover') === -1
        },
        filters: {
            formatDate(time) {
                let date = new Date(time)
                return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
            },

        },
        methods: {
            fetchData(currentPage, type, operator, oldValue, newValue) {
                this.listLoading = true
                findLogByPage(currentPage, type, operator, oldValue, newValue).then(response => {
                    this.list = response.data.list
                    for (let i = 0; i < this.list.length; i++) {
                        this.list[i].newValue = this.list[i].newValue ? this.formatJson(this.list[i].newValue) : '无';
                        this.list[i].oldValue = this.list[i].oldValue ? this.formatJson(this.list[i].oldValue) : '无';
                    }
                    this.totalNumber = response.data.totalNum
                    this.listLoading = false
                })
            },
            formatJson(json, options) {
                if (json == '') return '';
                var reg = null,
                    formatted = '',
                    pad = 0,
                    PADDING = '    '; // one can also use '\t' or a different number of spaces

                // optional settings
                options = options || {};
                // remove newline where '{' or '[' follows ':'
                options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
                // use a space after a colon
                options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

                // begin formatting...
                if (typeof json !== 'string') {
                    // make sure we start with the JSON as a string
                    json = JSON.stringify(json);
                } else {
                    // is already a string, so parse and re-stringify in order to remove extra whitespace
                    json = JSON.parse(json);
                    json = JSON.stringify(json);
                }

                // add newline before and after curly braces
                reg = /([\{\}])/g;
                json = json.replace(reg, '\r\n$1\r\n');

                // add newline before and after square brackets
                reg = /([\[\]])/g;
                json = json.replace(reg, '\r\n$1\r\n');

                // add newline after comma
                reg = /(\,)/g;
                json = json.replace(reg, '$1\r\n');

                // remove multiple newlines
                reg = /(\r\n\r\n)/g;
                json = json.replace(reg, '\r\n');

                // remove newlines before commas
                reg = /\r\n\,/g;
                json = json.replace(reg, ',');

                // optional formatting...
                if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
                    reg = /\:\r\n\{/g;
                    json = json.replace(reg, ':{');
                    reg = /\:\r\n\[/g;
                    json = json.replace(reg, ':[');
                }
                if (options.spaceAfterColon) {
                    reg = /\:/g;
                    json = json.replace(reg, ': ');
                }

                $.each(json.split('\r\n'), function (index, node) {
                    var i = 0,
                        indent = 0,
                        padding = '';

                    if (node.match(/\{$/) || node.match(/\[$/)) {
                        indent = 1;
                    } else if (node.match(/\}/) || node.match(/\]/)) {
                        if (pad !== 0) {
                            pad -= 1;
                        }
                    } else {
                        indent = 0;
                    }

                    for (i = 0; i < pad; i++) {
                        padding += PADDING;
                    }

                    formatted += padding + node + '\r\n';
                    pad += indent;
                });
                return formatted;
            },
            // 点击查询
            handleSearch() {
                this.fetchData(this.currentPage, this.type, this.operator, this.oldValue, this.newValue)
            },
            // 点击重置
            handleReset() {
                this.type = ''
                this.operator = ''
                this.oldValue = ''
                this.newValue = ''
                this.fetchData(this.currentPage)
            },
            // 点击还原
            handleRecover(row) {
                this.$confirm('是否还原该操作?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'info',
                    center: true
                }).then(() => {
                    recover(row.id).then(response => {
                        this.$notify.success({
                            title: '操作提示',
                            message: '还原成功'
                        })
                        this.fetchData(this.currentPage, this.type, this.operator, this.oldValue, this.newValue)
                    })
                }).catch(() => {
                    this.$notify.error({
                        title: '操作提示',
                        message: '还原失败'
                    })
                    this.fetchData(this.currentPage, this.type, this.operator, this.oldValue, this.newValue)
                })
            },
            // 序号自增
            indexMethod(index) {
                return (this.currentPage - 1) * 10 + (index + 1)
            },
            // 翻页
            handleCurrentChange(val) {
                this.currentPage = parseInt(`${val}`)
                this.fetchData(this.currentPage, this.type, this.operator, this.oldValue, this.newValue)
            }
        }
    }
</script>

<style scoped>
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

    pre {
        display: block;
        padding: 9.5px;
        margin: 0 0 10px;
        font-size: 13px;
        line-height: 1.42857143;
        word-break: break-all;
        word-wrap: break-word;
        color: #333;
        background-color: #f5f5f5;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
</style>
