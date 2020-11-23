<template>
    <div class="elastic_search_index">
        <el-card>
            <div style="margin: 10% 20% 5% 20%;">
            <el-input placeholder="请输入内容" v-model="content" class="input-with-select">
                <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
            </div>

            <div style="margin: 10% 10% 5% 10%;">
                <el-card shadow="never" class="body_content">

                </el-card>
            </div>
        </el-card>
    </div>
</template>
<script>
    import ElCard from "../../../node_modules/element-ui/packages/card/src/main.vue";

    export default {
        components: {ElCard},
        data(){
            return{
                content:"",
            }
        },
        methods:{

        },
    }
</script>
<style lang="scss" scoped>
    .elastic_search_index{
       .body_content{
          height: 600px;
       }
    }
</style>
