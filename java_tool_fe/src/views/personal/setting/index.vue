<template>
    <div class="personal-setting-content">
        <div class="base-info">
            <div class="ri-row">
                <span class="title">用户名：</span>
                <span class="content">{{userData.username}}</span>
            </div>
            <div class="ri-row">
                <span class="title">手机号：</span>
                <span class="content">{{userData.tel}}</span>
            </div>
            <div class="ri-row">
                <span class="title">公司邮箱：</span>
                <span class="content">{{userData.email}}</span>
            </div>
            <div class="ri-row">
                <span class="title">所属角色：</span>
                <span class="content" v-for="item in userData.role">{{item.name}}<br/></span>
            </div>
            <div class="ri-row">
                <span class="title">状态：</span>
                <span class="content" v-if="userData.status === 1">正常</span>
                <span class="content" v-else>冻结</span>
            </div>
            <div class="ri-row">
                <span class="title">描述：</span>
                <span class="content">{{userData.describe}}</span>
            </div>
        </div>
        <div class="change-password">
            <el-button v-if="!showForm" type="primary" @click="handleChangePass" size="medium">修改密码</el-button>
            <el-form v-if="showForm" :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="120px"
                     class="demo-ruleForm">
                <el-form-item label="旧密码：" prop="opass" v-if="!shownewPsw">
                    <el-input type="password" v-model="ruleForm2.opass" auto-complete="off" style="width: 20%"
                              size="small"></el-input>
                    <el-button type="primary" @click="handleValid" size="small">校验</el-button>
                </el-form-item>
                <el-form-item label="新密码：" prop="pass" v-if="shownewPsw">
                    <el-input type="password" v-model="ruleForm2.pass" auto-complete="off" style="width: 20%"
                              size="small"></el-input>
                </el-form-item>
                <el-form-item label="确认密码：" prop="checkPass" v-if="shownewPsw">
                    <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" style="width: 20%"
                              size="small"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="resetForm('ruleForm2')" size="medium" v-if="shownewPsw">重置</el-button>
                    <el-button type="primary" @click="submitForm('ruleForm2')" size="medium" v-if="shownewPsw">提交
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {getUserName} from '@/utils/auth'
    import {getInfo} from '@/api/login'
    import {checkPwd, updatePassword} from '@/api/system'

    export default {
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入新密码'));
                } else {
                    if (this.ruleForm2.checkPass !== '') {
                        this.$refs.ruleForm2.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm2.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                showForm: false,
                shownewPsw: false,
                userData: {
                    role: [],
                    tel: '',
                    email: '',
                    username: '',
                    status: '',
                    describe: ''
                },
                ruleForm2: {
                    opass: '',
                    pass: '',
                    checkPass: ''
                },
                rules2: {
                    pass: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: validatePass2, trigger: 'blur'}
                    ]
                }
            }
        },
        created() {
            this.getUserInfo()
        },
        methods: {
            // 获取用户信息
            getUserInfo() {
                getInfo(getUserName()).then(response => {
                    this.userData.role = response.data.roles;
                    this.userData.tel = response.data.telephone;
                    this.userData.email = response.data.mail;
                    this.userData.status = response.data.status;
                    this.userData.username = response.data.username;
                    this.userData.describe = response.data.remark;
                })
            },
            // 验证旧密码
            handleValid() {
                checkPwd(this.ruleForm2.opass).then(response => {
                    if (response.code === 200) {
                        this.shownewPsw = true;
                    }
                })
            },
            // 重置密码
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        updatePassword(this.ruleForm2.pass).then(response => {
                            if (response.code === 200) {
                                // UI上的显示
                                this.$notify.success({
                                    title: '操作提示',
                                    message: '修改成功！'
                                })
                                setTimeout(() => {
                                    this.shownewPsw = false;
                                    this.ruleForm2.opass = '';
                                    this.showForm = false;
                                    this.$refs[formName].resetFields();
                                }, 1000)
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            // 点击重置按钮
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            // 点击修改密码按钮
            handleChangePass() {
                this.showForm = true;
            }
        }
    }
</script>
<style scoped="scoped">
    .personal-setting-content .ri-row .title {
        width: 100px;
        display: flex;
        flex-direction: row-reverse;
    }

    .personal-setting-content .ri-row .content {
        margin-left: 10px;
    }

    .personal-setting-content .ri-row {
        margin: 15px 10px;
        display: flex;
    }

    .personal-setting-content .base-info {
        border-bottom: 1px dashed #000;
        width: 80%;
        margin-left: 40px;
    }

    .personal-setting-content .change-password {
        margin: 20px 40px;
    }

    .personal-setting-content .el-form-item {
        margin-bottom: 15px;
    }
</style>

