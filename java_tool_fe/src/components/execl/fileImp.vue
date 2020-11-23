<!--导入-->
<template>
    <div class="repo-imp-index">

        <el-dialog
            :visible.sync="dialogVisible"
            :title="dialogTitle"
            center
            width="40%">
            <el-form label-width="100px" label-position="left">
                <el-form-item
                    label="文件">
                    <el-upload
                        v-if="dialogVisible"
                        ref="upload"
                        action=""
                        :auto-upload="false"
                        :limit="1"
                        :on-exceed="handleExceed"
                        :before-upload="beforeUpload"
                        :http-request="httpRequest"
                        drag>
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                        <div class="el-upload__tip" slot="tip">点击
                            <el-button type="text" @click="templateDownload">下载模板</el-button>
                            ，按格式填写
                        </div>
                    </el-upload>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="danger" icon="el-icon-error" @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" icon="el-icon-success" @click="submitForm">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>
<script>
    import {poiSaveFile} from '@/api/fileMan'

    export default {
        name: 'repoImp',
        props: {
            execlImplParams: {
                impType: {
                    type: String
                },
                implUrl: {
                    type: String
                },
            },

        },
        data() {
            return {
                dialogVisible: false,
                dialogTitle: '',
            }
        },
        methods: {

            // 上传文件之前的钩子，附件上传前的校验
            beforeUpload(file) {
                let suffix = file.name.substring(file.name.lastIndexOf('.'))
                const excel = suffix === '.xls' || suffix === '.xlsx'
                const isLt3M = file.size < 3 * 1024 * 1024

                if (!excel) {
                    this.$message.error('请选择Excel文件进行上传！')
                }
                if (!isLt3M) {
                    this.$message.error('上传Excel文件不能超过 3MB！')
                }
                return excel && isLt3M
            },
            // 文件超出个数限制时的钩子
            handleExceed(files, fileList) {
                this.$message.warning('单次只允许上传1个文件！')
            },
            // 覆盖默认的上传行为，可以自定义上传的实现
            httpRequest(param) {
                let $loading = this.$loading({
                    lock: true,
                    text: '正在提交，请耐心等待',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                })
                let formData = new FormData()
                formData.append('impType', this.impType)
                formData.append('excel', param.file)
                poiSaveFile(formData).then(response => {
                    if (response.data.errExecl!='') {
                    }
                    this.$emit("handleSearch")
                    this.dialogVisible = false
                }).finally(() => {
                    $loading.close()
                })
            },
            // 导入
            handleImp() {
                this.dialogTitle = '导入'
                this.dialogVisible = true
            },
            // 模板下载
            templateDownload() {
                switch (this.execlImplParams.impType) {
                    case 'test':
                        window.open('static/file/PoiImpExample.xlsx')
                        break
                    default:
                        break
                }
            },
            // 确定导入表单事件
            submitForm() {
                this.$refs.upload.submit();
            },
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
    .repo-imp-index {

    }
</style>
