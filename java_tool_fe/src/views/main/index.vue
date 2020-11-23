<template>
    <div class="dashboard-container">
        <div class="top">
            <el-card class="box-card" :body-style="{ padding: '0px' }">
                <div class="img-div">
                    <img src="../../assets/images/img1.png" class="image">
                    <span class="num">89</span>
                    <span class="text">元数据总量</span>
                </div>
                <div class="more" @click="handleToMetadata">
                    <span>更多</span>
                    <el-button type="text" class="button">
                        <img src="../../assets/images/icon.png" class="image">
                    </el-button>
                </div>
            </el-card>

            <el-card class="box-card" :body-style="{ padding: '0px' }">
                <div class="img-div">
                    <img src="../../assets/images/img2.png" class="image">
                    <span class="num">7</span>
                    <span class="text">稽核任务数量</span>
                </div>
                <div class="more" @click="handleToAudit">
                    <span>更多</span>
                    <el-button type="text" class="button">
                        <img src="../../assets/images/icon.png" class="image">
                    </el-button>
                </div>
            </el-card>

            <el-card class="box-card" :body-style="{ padding: '0px' }">
                <div class="img-div">
                    <img src="../../assets/images/img3.png" class="image">
                    <span class="num">12</span>
                    <span class="text">标准文档数量</span>
                </div>
                <div class="more" @click="handleToDocument">
                    <span>更多</span>
                    <el-button type="text" class="button">
                        <img src="../../assets/images/icon.png" class="image">
                    </el-button>
                </div>
            </el-card>

            <el-card class="box-card" :body-style="{ padding: '0px' }">
                <div class="img-div">
                    <img src="../../assets/images/img4.png" class="image">
                    <span class="num">2</span>
                    <span class="text">数据仓库总量</span>
                </div>
                <div class="more" @click="handleToAssert">
                    <span>更多</span>
                    <el-button type="text" class="button">
                        <img src="../../assets/images/icon.png" class="image">
                    </el-button>
                </div>
            </el-card>
        </div>

        <!--TODO: 图标统计部分-->
        <div class="bottom">
            <el-row>
                <el-col :span="13" style="padding-right: 15px;">
                    <el-card>
                        <div slot="header">
                            <span>数据增长趋势分析</span>
                        </div>
                        <div style="background-color: white ;width: 100%;height: 300px" id="countResult"></div>
                    </el-card>

                </el-col>
                <el-col :span="11">
                    <el-card>
                        <div slot="header">
                            <span>问题数据来源占比分析</span>
                        </div>
                        <div style="background-color: white ;width: 100%;height: 300px" id="countResult2"></div>
                    </el-card>
                </el-col>
            </el-row>

            <el-row style="margin-top: 15px;">
                <el-col :span="11" style="padding-right: 15px;">
                    <el-card>
                        <div slot="header">
                            <span>数据合规性同比分析</span>
                        </div>
                        <div style="background-color: white ;width: 100%;height: 300px" id="countResult3"></div>
                    </el-card>
                </el-col>
                <el-col :span="13">
                    <el-card>
                        <div slot="header">
                            <span>元数据增长趋势分析</span>
                        </div>
                        <div style="background-color: white ;width: 100%;height: 300px" id="countResult4"></div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    let echarts = require('echarts/lib/echarts')
    require('echarts/lib/chart/bar')
    require('echarts/lib/chart/pie')
    require('echarts/lib/chart/line')
    require('echarts/lib/chart/lines')
    require('echarts/lib/chart/graph')
    require('echarts/lib/component/geo')
    require('echarts/lib/component/tooltip')
    require('echarts/lib/component/title')
    require('echarts/lib/component/legend')
    require('echarts/lib/component/title')

    export default {
        mounted() {
            this.drawCylinder()
            this.drawLine()
            this.drawCylinder2()
            this.drawCirle()
        },
        data() {
            return {}
        },
        methods: {
            drawCylinder() {
                // 基于准备好的dom，初始化echarts实例
                let countResult = echarts.init(document.getElementById('countResult'))
                // 绘制图表
                countResult.setOption({
                    backgroundColor: '#FFFFFF',
                    title: {
                        text: '数据增长趋势统计分析结果',
                        textStyle: {
                            fontSize: 14
                        }
                    },
                    tooltip: {                          // 提示框组件
                        trigger: 'axis',
                        axisPointer: { // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    legend: {
                        data: ['2017', '2018', '增长率'],
                        top: '18'
                    },
                    grid: {
                        left: '3%',
                        right: '5%',
                        bottom: '3%',
                        containLabel: true,
                        show: false                 // 网格边框是否显示，上和右边框
                    },
                    toolbox: {
                        feature: {
                            dataView: { show: false, readOnly: false },        // 数据试图是否在控件中显示
                            //magicType: {show: true, type: ['stack', 'tiled']},
                            //restore: {show: true},
                            saveAsImage: { show: true }
                        }
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: true,          // 坐标轴两边留白
                        splitLine: {                // 网格线 x轴对应的是否显示
                            show: false
                        },
                        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                    },

                    yAxis: [                            // 双y坐标轴
                        {
                            name: '数量',
                            type: 'value',
                            splitLine: {                // 网格线 y轴对应的是否显示
                                show: false
                            },
                            axisLabel: {
                                formatter: '{value}'
                            }
                        },
                        {
                            name: '增长率 (%)',
                            //nameLocation: 'start',
                            splitLine: {                // 网格线 y轴对应的是否显示
                                show: false
                            },
                            min: 0,
                            max: 300,                       // growing rate upper limit
                            type: 'value',
                            //top:10,
                            inverse: false,
                            axisLine: {
                                lineStyle: {
                                    color: '#2f4554'

                                }
                            }
                        }],

                    series: [
                        {
                            name: '2017',
                            type: 'bar',
                            color: '#00BFFF',
                            //stack: '总量',
                            markPoint: {

                                data: [
                                    { type: 'max', name: '最大值' },
                                    { type: 'min', name: '最小值' }
                                ]
                            },
                            markLine: {
                                data: [
                                    { type: 'average', name: '平均值' }
                                ]
                            },
                            data: [1741.9, 977, 1742.2, 1431.1, 1636.2, 1447, 1711.7, 1921.2, 2609.6, 3332.6, 3647.3, 2498.1]
                        },
                        {
                            name: '2018',
                            type: 'bar',
                            color: '#DC143C',
                            //stack: '总量',
                            markPoint: {
                                data: [
                                    { type: 'max', name: '最大值' },
                                    { type: 'min', name: '最小值' }
                                ]
                            },
                            markLine: {

                                data: [
                                    { type: 'average', name: '平均值' }
                                ]
                            },
                            data: [2609, 1162.9, 2942.9, 5174.6, 5114.4, 5065.8, 3956.1, 3691.1, 4637.6, 4603.8, 6401.1, 4988.4]
                        },
                        {
                            name: '增长率',
                            type: 'line',
                            yAxisIndex: 1,              // yAxisIndex 1 表示第二个y轴，默认为0
                            color: '#FFD700',
                            //stack: '总量',
                            markPoint: {
                                data: [
                                    { type: 'max', name: '最大值' }
                                    //{type : 'min', name : '最小值'}
                                ]
                            },
                            data: [49.8, 19, 68.9, 261.6, 212.6, 250.1, 131.1, 92.1, 77.7, 38.1, 75.5, 99.7]
                        }
                    ]
                })
            },
            drawCirle() {
                let countResult2 = echarts.init(document.getElementById('countResult2'))
                countResult2.setOption({
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}: {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'left',
                        data: ['正确性', '完整性', '一致性', '大数据仓库', '开发者平台库', '劳资纠纷系统库', '电子档案袋库', '用户画像库', '精准服务系统库', '人社知识库', '其他']
                    },
                    series: [
                        {
                            name: '访问来源',
                            type: 'pie',
                            center: ['60%', '50%'],
                            selectedMode: 'single',
                            radius: [0, '30%'],
                            label: {
                                normal: {
                                    position: 'inner'
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            data: [
                                { value: 335, name: '正确性', selected: true },
                                { value: 679, name: '完整性' },
                                { value: 1548, name: '一致性' }
                            ]
                        },
                        {
                            name: '访问来源',
                            type: 'pie',
                            center: ['60%', '50%'],
                            radius: ['40%', '55%'],
                            data: [
                                { value: 335, name: '大数据仓库' },
                                { value: 310, name: '开发者平台库' },
                                { value: 234, name: '劳资纠纷系统库' },
                                { value: 135, name: '电子档案袋库' },
                                { value: 1048, name: '用户画像库' },
                                { value: 251, name: '精准服务系统库' },
                                { value: 147, name: '人社知识库' },
                                { value: 102, name: '其他' }
                            ]
                        }
                    ]
                })
            },
            drawLine() {
                // 基于准备好的dom，初始化echarts实例
                let countResult3 = echarts.init(document.getElementById('countResult3'))
                countResult3.setOption({
                    backgroundColor: '#FFFFFF',                      // 背景色
                    title: {
                        text: '数据合规性统计分析结果',
                        textStyle: {
                            fontSize: 14
                        }
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['2017', '2018'],
                        top: '18'            // 距离容器顶端的距离
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    toolbox: {
                        feature: {
                            dataView: { show: false, readOnly: false },        // 数据试图是否在控件中显示
                            saveAsImage: { show: true }
                        }
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,         // 坐标轴两边留白策略
                        splitLine: {                // 网格线 x轴对应的是否显示
                            show: false
                        },
                        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                    },
                    yAxis: {
                        type: 'value',
                        name: '同比(%)',
                        min: 70,
                        max: 100,
                        interval: 10,
                        splitLine: {                // 网格线 y轴对应的是否显示
                            show: false
                        }
                    },
                    series: [
                        {
                            name: '2017',
                            type: 'line',
                            data: [88.29, 83.68, 89.64, 90.47, 90.21, 93.63, 94.07, 90.85, 90.32, 90.56, 86.69, 81.77]

                        },
                        {
                            name: '2018',
                            type: 'line',
                            data: [90.36, 86.21, 92.04, 89.91, 90.15, 90.38, 88.03, 88.99, 88.35, 87.18, 86.29, 81.23]
                        }
                    ]
                })
            },
            drawCylinder2() {
                let countResult4 = echarts.init(document.getElementById('countResult4'))
                countResult4.setOption({
                    backgroundColor: '#FFFFFF',
                    title: {
                        text: '元数据增长趋势分析统计结果',
                        //left:'center',              // title位置
                        textStyle: {
                            fontSize: 14
                        }
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: { // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    toolbox: {
                        feature: {
                            dataView: { show: false, readOnly: false },        // 数据试图是否在控件中显示
                            saveAsImage: { show: true }
                        }
                    },
                    legend: {
                        data: ['MySQL', 'Oracle', 'Hive', 'HBase'],
                        top: '18',
                        right: '20'
                    },
                    grid: {
                        left: '2%',
                        right: '9%',
                        bottom: '3%',
                        containLabel: true,
                        show: false                 // 网格边框是否显示，上和右边框
                    },

                    xAxis: [{
                        type: 'category',
                        splitLine: {                // 网格线 x轴对应的是否显示
                            show: false
                        },
                        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                    }],

                    yAxis: [{
                        name: '数量',
                        type: 'value',
                        splitLine: {                 // 网格线 y轴对应的是否显示
                            show: false
                        },
                        axisLabel: {
                            formatter: '{value}'
                        }
                    }],

                    series: [{
                        name: 'MySQL',
                        type: 'bar',
                        itemStyle: {
                            normal: { color: '#01949B' }
                        },
                        markLine: {
                            data: [
                                { type: 'average', name: '平均值' }
                            ]
                        },

                        data: [919, 455, 74, 91, 100, 107, 110, 327, 256, 456, 320, 127]
                    },
                        {
                            name: 'Oracle',
                            type: 'bar',
                            itemStyle: {
                                normal: { color: '#EBA954' }
                            },

                            markLine: {
                                data: [
                                    { type: 'average', name: '平均值' }
                                ]
                            },
                            data: [467, 261, 356, 387, 419, 227, 417, 413, 564, 583, 415, 666]
                        }, {
                            name: 'Hive',
                            type: 'bar',
                            itemStyle: {
                                normal: { color: '#C23531' }
                            },

                            markLine: {
                                data: [
                                    { type: 'average', name: '平均值' }
                                ]
                            },
                            data: [255, 259, 210, 118, 196, 140, 188, 204, 290, 186, 661, 468]
                        }, {
                            name: 'HBase',
                            type: 'bar',
                            itemStyle: {
                                normal: { color: '#6495ED' }
                            },

                            markLine: {
                                data: [
                                    { type: 'average', name: '平均值' }
                                ]
                            },
                            data: [230, 410, 330, 142, 137, 381, 30, 292, 100, 126, 140, 135]
                        }]
                })
            },

            // 跳转到代办人数量
            handleToMetadata() {
                this.$router.push({ path: '../metadata/search' })
            },
            // 跳转到机构数量
            handleToAudit() {
                this.$router.push({ path: '../quality/auditTask' })
            },
            // 跳转到登记人数
            handleToDocument() {
                this.$router.push({ path: '../standard/Document' })
            },
            // 跳转到黑名单
            handleToAssert() {
                this.$router.push({ path: '../assert/dataView' })
            }

        }
    }
</script>

<style>
    .dashboard-container {
        padding: 15px;
        z-index: 0;
        background-color: #f3f7fa;
    }

    .dashboard-container .top {
        width: 100%;
        height: 120px;
    }

    .dashboard-container .top .box-card {
        width: 24%;
        margin-right: 1%;
        float: left;
    }

    .dashboard-container .top .box-card .img-div {
        position: relative;
        color: #fff;
    }

    .dashboard-container .top .box-card .img-div .num {
        font-weight: bold;
        font-size: 35px;
        position: absolute;
        top: 12px;
        right: 20px;
    }

    .dashboard-container .top .box-card .img-div .text {
        font-weight: bold;
        font-size: 16px;
        position: absolute;
        top: 60px;
        right: 20px;
    }

    .dashboard-container .top .box-card .more {
        cursor: pointer;
        padding: 12px;
        background-color: #f5f5f5;
        color: #409EFF
    }

    .dashboard-container .bottom {
        clear: both;
        margin-top: 35px;
        height: 360px;
    }

    @media screen and (min-width: 1600px) {
        .dashboard-container .bottom {
            margin-top: 65px;
        }
    }

    @media screen and (min-width: 2000px) {
        .dashboard-container .bottom {
            margin-top: 85px;
        }
    }

    .dashboard-container .bottom .left {
        width: 60%;
        margin-right: 1%;
        border: 1px solid #dddddd;
        float: left;
        background-color: #fff;
    }

    .dashboard-container .bottom .right {
        width: 38%;
        float: left;
        border: 1px solid #dddddd;
        background-color: #fff;
    }

    .dashboard-container .bottom .left .title, .dashboard-container .bottom .right .title {
        height: 40px;
        line-height: 40px;
        background-color: #f5f5f5;
        padding-left: 12px;
        border-bottom: 1px solid #dddddd;
    }

    .dashboard-container .bottom .left #entryCharts, .dashboard-container .bottom .right #orderCharts {
        width: 100%;
        height: 350px;
        padding-top: 20px;
    }

    .dashboard-container .button {
        padding: 0;
        float: right;
    }

    .dashboard-container .image {
        width: 100%;
        display: block;
    }
</style>
