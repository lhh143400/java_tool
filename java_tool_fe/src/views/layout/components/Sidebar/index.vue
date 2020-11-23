<template>
    <el-scrollbar wrap-class="scrollbar-wrapper">
        <el-menu
            :show-timeout="200"
            :default-active="$route.path"
            :collapse="isCollapse"
            mode="vertical"
            background-color="#304156"
            text-color="#bfcbd9"
            active-text-color="#409EFF"
        >
            <template>
                <div @click="barToggle" class="scroll-bar-top"
                     style="width: 180px;height: 100px;background-color: #0aa095;text-align: center">
                    <img v-show="isShowImg1" class="img1" :src="imageUrl1" style="margin-top: 33px;"/>
                    <img v-show="isShowImg2" class="img2" :src="imageUrl2" style="margin-top: 26px;"/>
                </div>
            </template>
            <sidebar-item v-for="route in routes" :key="route.name" :item="route" :base-path="route.path"/>
        </el-menu>
    </el-scrollbar>
</template>

<script>
    import { mapGetters } from 'vuex'
    import SidebarItem from './SidebarItem'
    import jQuery from 'jquery'

    export default {
        components: { SidebarItem },
        data() {
            return {
                imageUrl1: 'static/images/logo_ylz.png',
                imageUrl2: 'static/images/logo_ylz_mini.png',
                isShowImg1: true,
                isShowImg2: false
            }
        },
        computed: {
            ...mapGetters([
                'sidebar'
            ]),
            routes() {
                // return this.$router.options.routes
                return JSON.parse(localStorage.getItem('functions'))
            },
            isCollapse() {
                return !this.sidebar.opened
            }
        },
        mounted() {
            this.$nextTick(function () {
                this.barToggle()
            })
        },
        methods: {
            barToggle() {
                if (jQuery('.hamburger').hasClass('is-active')) {
                    jQuery('.scroll-bar-top').css('width', '180px')
                    this.isShowImg1 = true
                    this.isShowImg2 = false
                } else {
                    jQuery('.scroll-bar-top').css('width', '36px')
                    this.isShowImg1 = false
                    this.isShowImg2 = true
                }
            }
        },
        updated() {
            this.barToggle()
        }
    }
</script>
