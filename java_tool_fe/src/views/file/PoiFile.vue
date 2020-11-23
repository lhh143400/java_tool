<!--poi 文件导入测试-->
<template>
    <div>
        <el-button @click="importTemplate" >导入</el-button>
        <file-imp ref="impExecl" :execlImplParams="execlImplParams"/>
    </div>
</template>
<script>
    import fileImp from '@/components/execl/fileImp'
    import ElButton from "../../../node_modules/element-ui/packages/button/src/button.vue";
    export default {
        components:{
            ElButton,
            fileImp
        },
        data(){
            return{
                execlImplParams:{
                    impType:"test"
                }
            }
        },
        methods:{
            //导入模板
            importTemplate(){
              this.$refs.impExecl.handleImp();
            }
        }
    }
</script>
