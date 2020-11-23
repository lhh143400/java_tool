<template>
    <div class="file_page">
        <el-card>

           <!-- 查询-->
            <el-row :gutter="10">
                    <el-col :span="12">
                        <el-form :inline="true" size="small">
                            <el-form-item label="文件名称">
                                <el-input v-model="fileName" placeholder="文件名称" @keyup.enter.native="handleSearch"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <el-col :span="12">
                        <div style="float: right;">
                            <el-button type="primary" size="small" icon="el-icon-plus" @click="handleAdd">新增</el-button>
                        </div>
                    </el-col>
            </el-row>

           <!--表格-->
            <div class="table-body">
                <el-table
                    :data="list"
                    border
                    stripe
                    tooltip-effect="dark"
                    v-loading="listLoading"
                    element-loading-text="数据加载中..."
                    style="width: 100%;">
                    <el-table-column
                        type="index"
                        :index="indexMethod"
                        label="序号"
                        align="center"
                        width="60">
                    </el-table-column>
                    <el-table-column
                        prop="fileName"
                        label="文件名称"
                        width="200"
                        align="center"
                        show-overflow-tooltip>
                        <template slot-scope="scope">
                            <el-link type="primary" :href="scope.row.fileUrl" target="_blank">{{ scope.row.fileName }}</el-link>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="fileSize"
                        label="文件大小"
                        width=""
                        align="center"
                        show-overflow-tooltip>
                    </el-table-column>
                    <el-table-column
                        label="操作"
                        align="center"
                        width="220">
                        <template slot-scope="scope">
                            <el-button
                                size="mini"
                                icon="el-icon-delete"
                                @click="handleDelete(scope.$index,scope.row)"
                                type="danger">删除
                            </el-button>
                            <el-button
                                size="mini"
                                icon=""
                                @click="onPreview(scope.$index,scope.row)"
                                type="danger">预览
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div style="float:right">
                    <el-pagination
                        :hide-on-single-page="false"
                        @current-change="handleCurrentChange"
                        :current-page.sync="currentPage"
                        :page-size="10"
                        layout="total,prev,pager,next,jumper"
                        :total="totalNumber">
                    </el-pagination>
                </div>
            </div>
        </el-card>

        <el-image-viewer
            v-if="showViewer"
            :on-close="closeViewer"
            :url-list="[url]" />

        <!-- 上传附件对话框 -->
        <el-dialog :visible.sync="dialogVisible" :title="dialogTitle"center width="50%">

            <el-form ref="attachmentRepoForm" :model="fileManForm" :rules="fileManRule" label-width="100px" label-position="left">
                <el-form-item label="附件" prop="content">
                    <el-upload
                        v-if="dialogVisible"
                        class="attachment-uploader"
                        action=""
                        :auto-upload="true"
                        :limit="3"
                        :on-exceed="handleExceed"
                        :before-upload="beforeUpload"
                        :before-remove="beforeRemove"
                        :http-request="httpRequest"
                        drag
                        multiple>
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                        <div class="el-upload__tip" slot="tip">最多同时上传3个文件，且单文件大小控制在3M以内</div>
                    </el-upload>
                </el-form-item>

                <el-form-item label="备注" prop="fileDesc">
                    <el-input type="textarea" v-model="fileManForm.fileDesc" :rows="6" placeholder="请输入备注说明"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="danger" icon="el-icon-error" @click="dialogVisible = false">取 消</el-button>
                <el-button
                    type="primary"
                    icon="el-icon-success"
                    @click="submitForm('attachmentRepoForm')">上 传
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import ElCard from "../../../node_modules/element-ui/packages/card/src/main.vue";
    import ElRow from "element-ui/packages/row/src/row";
    import ElInput from "../../../node_modules/element-ui/packages/input/src/input.vue";
    import {saveFile,queryFile} from '@/api/fileMan';
    import ElImageViewer from 'element-ui/packages/image/src/image-viewer';

    export default {
        components: {
            ElInput,
            ElRow,ElImageViewer,
            ElCard},
        data(){
            return{
                fileName:"",
                currentPage: 1,
                totalNumber: 0,

                list:null,
                listLoading:false,

                dialogVisible: false,
                dialogTitle: '',
                fileManForm:{
                    fileList:[],
                    fileDesc:""
                },
                fileManRule:{
                    fileDesc:[{required:true,message:'请填写备注',trigger:'blur'}]
                },


                showViewer:false,
                url:"",
            }
        },
        created(){
            this.handleSearch();
        },
        methods:{



            // 附件上传表单提交
            submitForm(form) {
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        let $loading = this.$loading({
                            lock: true,
                            text: '正在提交，请耐心等待',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        })
                        let formData = new FormData()
                        if (this.fileManForm.fileList.length === 0) {
                            this.$message.warning('请选择需要上传的文件！')
                            $loading.close()
                            return
                        }
                        if (this.fileManForm.fileList.length > 3) {
                            this.$message.warning('最多同时上传3个文件！')
                            $loading.close()
                            return
                        }
                        for (let file of this.fileManForm.fileList) {
                            if (MongodbFile.size > (1024 * 1024 * 3)) {
                                this.$message.warning('单文件大小不能超过3M！')
                                $loading.close()
                                return
                            }
                            formData.append('fileList', MongodbFile)
                        }
                        saveFile(formData).then(response => {
                            this.$message({
                                message: response.message,
                                type: 'success',
                                duration: 5000,
                                center: true
                            })
                            this.dialogVisible = false
                            this.handleSearch()
                        }).finally(() => {
                            $loading.close()
                        })
                    }
                })
            },


            //新增弹窗
            handleAdd(){
                this.resetFormData()
                this.dialogTitle = '附件上传'
                this.dialogVisible = true
            },
            // 重置附件表单数据
            resetFormData() {
                this.fileManForm.fileList = []
                this.fileManForm.fileDesc = "";
            },
            //查询
            handleSearch(){
                this.fetchData();
            },
            //获取查询数据
            fetchData(fileName){
              this.listLoading=true;
                queryFile(fileName).then(response=>{
                    this.list=response.data;
                    this.listLoading=false;
                })
            },

            // 覆盖默认的上传行为，可以自定义上传的实现
            httpRequest() {},
            // 上传文件之前的钩子，附件上传前的校验
            beforeUpload(file,fileList) {
                console.log(MongodbFile,fileList)
                this.fileManForm.fileList.push(MongodbFile)
            },
           // 删除文件之前的钩子
            beforeRemove(file) {
                let delFile = MongodbFile.raw
                for (let i = 0; i < this.fileManForm.fileList.length; i++) {
                    if (delFile.uid === this.fileManForm.fileList[i].uid) {
                        this.fileManForm.fileList.splice(i, 1)
                        break
                    }
                }
            },
           // 文件超出个数限制时的钩子
            handleExceed() {
                this.$message.warning('最多同时上传3个文件！')
            },

            // 关闭查看器
            closeViewer() {
                this.showViewer = false
            },
            onPreview(index,row) {
                this.showViewer = true;
                this.url=row.fileUrl
            },

            // 翻页
            handleCurrentChange(val) {
                this.currentPage = parseInt(`${val}`)
                this.handleSearch();
            },
            // 序号自增
            indexMethod(index) {
                return (this.currentPage - 1 ) * 10 + (index + 1);
            },
        },
    }
</script>
<style lang="scss" scoped>

</style>
