<template>
    <div class="login-container">
        <el-form autoComplete="on" :model="loginForm" :rules="loginRules" ref="loginForm" label-position="left"
                 label-width="0px"
                 class="card-box login-form">
            <div class="form-bg"></div>
            <h3 class="title">易联众数据治理平台</h3>
            <el-form-item prop="username">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="usernew"/>
        </span>
                <el-input name="username" type="text" v-model="loginForm.username" autoComplete="on"
                          placeholder="请输入用户名"/>
            </el-form-item>
            <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="psw"></svg-icon>
        </span>
                <el-input name="password" :type="pwdType" @keyup.enter.native="handleLogin" v-model="loginForm.password"
                          autoComplete="on"
                          placeholder="请输入密码"></el-input>
                <span class="show-pwd" @click="showPwd"><svg-icon :icon-class='eye'/></span>
            </el-form-item>
            <el-form-item class="login-btn">
                <el-button type="primary" style="width:100%;" :loading="loading" @click.native.prevent="handleLogin">
                    登 录
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import { isvalidUsername } from '@/utils/validate'
    import { getFunctions } from '@/api/login'
    import { getRoutes } from '@/utils/router'

    export default {
        name: 'Login',
        data() {
            const validateUsername = (rule, value, callback) => {
                if (!isvalidUsername(value)) {
                    callback(new Error('请输入正确的用户名'))
                } else {
                    callback()
                }
            }
            const validatePass = (rule, value, callback) => {
                if (value.length < 5) {
                    callback(new Error('密码不能小于5位'))
                } else {
                    callback()
                }
            }
            return {
                loginForm: {
                    username: '',
                    password: ''
                },
                loginRules: {
                    /* username: [{ required: true, trigger: 'blur', validator: validateUsername }],*/
                    username: [{ required: true, trigger: 'blur' }],
                    password: [{ required: true, trigger: 'blur' }]
                },
                loading: false,
                pwdType: 'password',
                eye: 'eye-close'
            }
        },
        methods: {
            showPwd() {
                if (this.pwdType === 'password') {
                    this.pwdType = ''
                    this.eye = 'eye-open'
                } else {
                    this.pwdType = 'password'
                    this.eye = 'eye-close'
                }
            },
            handleLogin() {
                this.$refs.loginForm.validate(valid => {
                    if (valid) {
                        this.loading = true
                        this.$store.dispatch('Login', this.loginForm).then(() => {
                            this.loading = false
                            // this.$router.push({ path: '/' })
                            getFunctions(this.loginForm.username).then(response => {
                                let extendsRoutes = getRoutes(response.data, this.$root)
                                // 保存菜单
                                localStorage.setItem('functions', JSON.stringify(extendsRoutes))
                                // 动态添加路由
                                this.$router.addRoutes(extendsRoutes)
                                // 跳转到应用界面
                                this.$router.push({ path: '/' })
                            })
                        }).catch(() => {
                            this.loading = false
                        })
                    } else {
                        console.log('error submit!!')
                        return false
                    }
                })
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss">
    @import "src/styles/mixin.scss";

    $bg: #2d3a4b;
    $dark_gray: #889aa4;
    $light_gray: #eee;

    .login-container {
        @include relative;
        height: 100vh;
        background-image: url(../../assets/images/logo_bg.png);
        input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px #fff inset !important;
            -webkit-text-fill-color: #283442 !important;
        }
        ::-webkit-input-placeholder {
            font-weight: normal;
        }
        :-moz-placeholder {
            font-weight: normal;
        }
        ::-moz-placeholder {
            font-weight: normal;
        }
        :-ms-input-placeholder {
            font-weight: normal;
        }
        input {
            background: transparent;
            border: 0px;
            -webkit-appearance: none;
            border-radius: 0px;
            padding: 12px 5px 12px 6px;
            color: #283442 !important;
            height: 42px;
            font-weight: bold;
        }
        .svg-container_login {
            font-size: 15px !important;
        }
        .el-input {
            display: inline-block;
            height: 42px;
            width: 85%;
        }
        .tips {
            font-size: 14px;
            color: #fff;
            margin-bottom: 10px;
        }
        .svg-container {
            padding: 0px 5px 6px 15px;
            color: $dark_gray;
            vertical-align: middle;
            width: 30px;
            display: inline-block;
            &_login {
                font-size: 14px;
            }
        }
        .title {
            font-size: 23px;
            color: $light_gray;
            margin: 0px auto 37px auto;
            text-align: center;
            font-weight: bold;
        }
        .login-form {
            position: absolute;
            left: 0;
            right: 0;
            width: 348px;
            padding: 41px 29px 42px 29px;
            margin: 120px auto;
            .form-bg {
                top: 0px;
                left: 0px;
                width: 348px;
                position: absolute;
                height: 312px;
                background-color: #fff;
                opacity: 0.2;
                border-radius: 4px;
            }
        }
        .el-form-item {
            border-radius: 5px;
            color: #454545;
            border: none;
            background-color: #fff;
            margin-bottom: 18px !important;
            .el-form-item__content {
                font-size: 15px !important;
                line-height: 26px !important;
            }
        }
        .login-btn {
            margin-top: 26px;
        }
        .show-pwd {
            position: absolute;
            right: 10px;
            top: 7px;
            font-size: 16px;
            color: $dark_gray;
            cursor: pointer;
            user-select: none;
        }
        .thirdparty-button {
            position: absolute;
            right: 35px;
            bottom: 28px;
        }
        .el-button {
            background-color: #0aa095;
            border-color: #0aa095 !important;
            font-size: 16px;
        }
    }
</style>


