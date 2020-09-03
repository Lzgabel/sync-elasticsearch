<!DOCTYPE html>
<html style="font-size: 250px;" lang="UTF-8">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>数据可视化</title>
    <link type="text/css" href="/loc/css/public.css" rel="stylesheet">
    <link type="text/css" href="/loc/css/icon.css" rel="stylesheet">
    <link type="text/css" href="/loc/css/index.css" rel="stylesheet">
    <script type="text/javascript">
        document.documentElement.style.fontSize = document.documentElement.clientWidth / 768 * 100 + 'px';
    </script>

    <script src="/js/echarts.min.js"></script>
    <style>
        .abc {

        }

    </style>
</head>


<body class="bg">
<div>
    <div class="title">数据可视化</div>
    <div class="leftMain">
        <div class="leftMain_top">
            <div class="leftMain_topIn">
                <ul>
                    <li>
                        <div class="liIn">
                            <h3>总计</h3>
                            <p class="shu"><span class="shu1">${all}</span><i>行</i></p>
                            <div class="zi"><span class="span1"></span><span></span></div>
                            <span class="border_bg_leftTop"></span>
                            <span class="border_bg_rightTop"></span>
                            <span class="border_bg_leftBottom"></span>
                            <span class="border_bg_rightBottom"></span>
                        </div>
                    </li>
                    <li>
                        <div class="liIn">
                            <h3>年平均</h3>
                            <p class="shu"><span class="shu2">${avg}</span><i>行</i></p>
                            <div class="zi"><span class="span1"></span><span></span></div>
                            <span class="border_bg_leftTop"></span>
                            <span class="border_bg_rightTop"></span>
                            <span class="border_bg_leftBottom"></span>
                            <span class="border_bg_rightBottom"></span>
                        </div>
                    </li>
                    <li>
                        <div class="liIn">
                            <h3>Accepted</h3>
                            <p class="shu"><span class="shu2">${ac}</span><i>行</i></p>
                            <div class="zi"><span class="span1"></span><span></span></div>
                            <span class="border_bg_leftTop"></span>
                            <span class="border_bg_rightTop"></span>
                            <span class="border_bg_leftBottom"></span>
                            <span class="border_bg_rightBottom"></span>
                        </div>
                    </li>
                    <li>
                        <div class="liIn">
                            <h3>Other</h3>
                            <p class="shu"><span class="shu4">${other}</span><i>行</i></p>
                            <div class="zi"><span class="span1"></span><span></span></div>
                            <span class="border_bg_leftTop"></span>
                            <span class="border_bg_rightTop"></span>
                            <span class="border_bg_leftBottom"></span>
                            <span class="border_bg_rightBottom"></span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>

        <div class="leftMain_middle">
            <div class="leftMain_middle_left">
                <div class="leftMain_middle_leftIn">
                    <h3>年提交量统计</h3>
                    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                    <div class="biaoge"
                         style="width: 100%; height: 25vh; -webkit-tap-highlight-color: transparent; user-select: none;"
                         id="chartmain" _echarts_instance_="ec_1598945994793">
                        <div style="position: relative; width: 750px; height: 242px; padding: 0px; margin: 0px; border-width: 0px; cursor: default;">
                            <canvas data-zr-dom-id="zr_0" width="750" height="242"
                                    style="position: absolute; left: 0px; top: 0px; width: 750px; height: 242px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); padding: 0px; margin: 0px; border-width: 0px;"></canvas>
                        </div>
                    </div>
                    <script type="text/javascript">
                        //window.onload = function () {
                        //指定图表的配置项和数据

                        //var dataAxis = ['1日', '2日', '3日', '4日', '5日', '6日', '7日', '8日', '9日', '10日', '11日', '12日', '13日', '14日', '15日'];
                        var dataAxis = [<#list dataAxis as item>${item}, </#list>];
                        console.log(dataAxis);
                        //var data = [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 200];
                        var data = [<#list data as item>${item}, </#list>];
                        var yMax = 0;
                        var dataShadow = [];

                        for (var i = 0; i < data.length; i++) {
                            dataShadow.push(yMax);
                        }

                        option = {
                            title: {
                                text: '',
                                subtext: ''
                            },
                            grid: {
                                x: 40,
                                y: 40,
                                x2: 20,
                                y2: 20,

                            },
                            tooltip: {
                                trigger: 'item'
                            },
                            xAxis: {
                                data: dataAxis,
                                axisLabel: {
                                    /*inside: true,*/
                                    interval: 0,
                                    textStyle: {
                                        color: '#fff',
                                        fontSize: 12

                                    }
                                },
                                axisTick: {
                                    show: false,
                                },
                                axisLine: {
                                    show: true,
                                    symbol: ['none', 'arrow'],
                                    symbolOffset: 12,
                                    lineStyle: {
                                        color: '#fff',
                                    }
                                },
                                z: 10
                            },
                            yAxis: {
                                type: 'value',
                                name: '单位：行',
                                axisLine: {
                                    show: true,
                                    symbol: ['none', 'arrow'],
                                    symbolOffset: 12,
                                    lineStyle: {
                                        color: '#fff',
                                    }
                                },
                                axisTick: {
                                    show: false
                                },
                                axisLabel: {
                                    textStyle: {
                                        color: '#fff',
                                        fontSize: 12
                                    }
                                }
                            },

                            dataZoom: [
                                {
                                    type: 'inside'
                                }
                            ],
                            series: [
                                { // For shadow
                                    type: 'bar',
                                    itemStyle: {
                                        color: 'rgba(0,0,0,0.05)'
                                    },
                                    barGap: '-100%',
                                    barCategoryGap: '40%',
                                    data: dataShadow,
                                    animation: false
                                },
                                {
                                    type: 'bar',
                                    itemStyle: {
                                        color: new echarts.graphic.LinearGradient(
                                            0, 0, 0, 1,
                                            [
                                                {offset: 0, color: '#0efdff'},
                                                {offset: 0.5, color: '#188df0'},
                                                {offset: 1, color: '#188df0'}
                                            ]
                                        )
                                    },
                                    emphasis: {
                                        itemStyle: {
                                            color: new echarts.graphic.LinearGradient(
                                                0, 0, 0, 1,
                                                [
                                                    {offset: 0, color: '#2378f7'},
                                                    {offset: 0.7, color: '#2378f7'},
                                                    {offset: 1, color: '#0efdff'}
                                                ]
                                            )
                                        }
                                    },
                                    data: data
                                }
                            ]
                        };

                        //获取dom容器
                        var myChart = echarts.init(document.getElementById('chartmain'));
                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                        // Enable data zoom when user click bar.
                        var zoomSize = 0;
                        myChart.on('click', function (params) {
                            // 可以在这去动态更新每个月详情
                            console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
                            myChart.dispatchAction({
                                type: 'dataZoom',
                                startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
                                endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
                            });
                        });
                        //};
                    </script>
                    <span class="border_bg_leftTop"></span>
                    <span class="border_bg_rightTop"></span>
                    <span class="border_bg_leftBottom"></span>
                    <span class="border_bg_rightBottom"></span>
                </div>
            </div>
            <div class="leftMain_middle_right">
                <div class="leftMain_middle_rightIn">
                    <h3>这里是标题</h3>
                    <div class="biaoge biaoge_pai" style="width:100%; height:25vh">
                        <div class="biaoge_paiIn">
                            <ul>
                                <li>
                                    <div class="liIn">
                                        <div class="liIn_left"><span class="bot"></span><span class="zi">第一名</span>
                                        </div>
                                        <div class="liIn_line">
                                            <style type="text/css">
                                                @-webkit-keyframes widthMove1 {
                                                    0% {width:0%; }
                                                    100% { width:50.5%; }
                                                }
                                            </style>
                                            <div class="line_lineIn" style="width:50.5%;"></div>
                                        </div>
                                        <p class="num">50.5%</p>
                                    </div>
                                </li>
                                <li>
                                    <div class="liIn liIn2">
                                        <div class="liIn_left"><span class="bot"></span><span class="zi">第二名</span>
                                        </div>
                                        <div class="liIn_line">
                                            <div class="line_lineIn" style="width:88.5%;"></div>
                                        </div>
                                        <p class="num">88.5%</p>
                                    </div>
                                </li>
                                <li>
                                    <div class="liIn liIn3">
                                        <div class="liIn_left"><span class="bot"></span><span class="zi">第三名</span>
                                        </div>
                                        <div class="liIn_line">
                                            <div class="line_lineIn" style="width:68.5%;"></div>
                                        </div>
                                        <p class="num">68.5%</p>
                                    </div>
                                </li>
                                <li>
                                    <div class="liIn liIn4">
                                        <div class="liIn_left"><span class="bot"></span><span class="zi">第四名</span>
                                        </div>
                                        <div class="liIn_line">
                                            <div class="line_lineIn" style="width:40.5%;"></div>
                                        </div>
                                        <p class="num">40.5%</p>
                                    </div>
                                </li>
                                <li>
                                    <div class="liIn liIn5">
                                        <div class="liIn_left"><span class="bot"></span><span class="zi">第五名</span>
                                        </div>
                                        <div class="liIn_line">
                                            <div class="line_lineIn" style="width:22.5%;"></div>
                                        </div>
                                        <p class="num">22.5%</p>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <span class="border_bg_leftTop"></span>
                    <span class="border_bg_rightTop"></span>
                    <span class="border_bg_leftBottom"></span>
                    <span class="border_bg_rightBottom"></span>
                </div>
            </div>
        </div>
        <div class="leftMain_middle">
            <div class="leftMain_middle_left">
                <div class="leftMain_middle_leftIn">
                    <h3>近6个月提交量</h3>
                    <div class="biaoge"
                         style="width: 100%; height: 25vh; -webkit-tap-highlight-color: transparent; user-select: none; position: relative;"
                         id="chartmain_zhe" _echarts_instance_="ec_1598945994794">
                        <div style="position: relative; width: 750px; height: 242px; padding: 0px; margin: 0px; border-width: 0px;">
                            <canvas data-zr-dom-id="zr_0" width="750" height="242"
                                    style="position: absolute; left: 0px; top: 0px; width: 750px; height: 242px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); padding: 0px; margin: 0px; border-width: 0px;"></canvas>
                        </div>
                        <div></div>
                    </div>
                    <script type="text/javascript">
                        //window.onload = function (){
                        //指定图表的配置项和数据
                        option = {
                            title: {
                                text: ''
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                textStyle: {
                                    color: '#fff',
                                    fontSize: 12,
                                },
                                right: '10%',
                                data: ['折线一']
                            },
                            grid: {
                                left: '3%',
                                right: '6%',
                                bottom: '3%',
                                containLabel: true
                            },
                            toolbox: {
                                feature: {
                                    //saveAsImage: {}
                                }
                            },
                            xAxis: {
                                type: 'category',
                                boundaryGap: false,
                                axisLabel: {
                                    /*inside: true,*/
                                    interval: 0,
                                    textStyle: {
                                        color: '#fff',
                                        fontSize: 12

                                    }
                                },
                                axisTick: {
                                    show: false,
                                },
                                axisLine: {
                                    show: true,
                                    symbol: ['none', 'arrow'],
                                    symbolOffset: 12,
                                    lineStyle: {
                                        color: '#fff',
                                    }
                                },
                                data: [<#list dateList as item>"${item}", </#list>]
                            },
                            yAxis: {
                                type: 'value',
                                name: '单位：行',
                                axisLine: {
                                    show: true,
                                    symbol: ['none', 'arrow'],
                                    symbolOffset: 12,
                                    lineStyle: {
                                        color: '#fff',
                                    }
                                },
                                axisTick: {
                                    show: false
                                },
                                axisLabel: {
                                    textStyle: {
                                        color: '#fff',
                                        fontSize: 12
                                    }
                                }
                            },
                            series: [
                                {
                                    name: '折线一',
                                    type: 'line',
                                    stack: '总量',
                                    data: [<#list lineData as item>${item}, </#list>],
                                    itemStyle: {
                                        normal: {
                                            color: "#0efdff",//折线点的颜色
                                            lineStyle: {
                                                color: "#0efdff",//折线的颜色
                                                width: 2,
                                            }
                                        },
                                    }
                                }
                            ]
                        };
                        //获取dom容器
                        var myChart = echarts.init(document.getElementById('chartmain_zhe'));
                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                        //};
                    </script>
                    <span class="border_bg_leftTop"></span>
                    <span class="border_bg_rightTop"></span>
                    <span class="border_bg_leftBottom"></span>
                    <span class="border_bg_rightBottom"></span>
                </div>
            </div>
            <div class="leftMain_middle_right">
                <div class="leftMain_middle_rightIn">
                    <h3>这里是标题</h3>
                    <div class="biaoge biaoge_bi" style="width:100%; height:25vh">
                        <ul>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu1">23</p>
                                    <p class="zi">今日收益比例</p>
                                </div>
                            </li>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu2">107</p>
                                    <p class="zi">本月收益比例</p>
                                </div>
                            </li>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu3">107</p>
                                    <p class="zi">历史收益比例</p>
                                </div>
                            </li>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu4">23</p>
                                    <p class="zi">今日收益比例</p>
                                </div>
                            </li>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu5">23</p>
                                    <p class="zi">本月收益比例</p>
                                </div>
                            </li>
                            <li>
                                <div class="liIn">
                                    <p class="shu shu6">23</p>
                                    <p class="zi">历史收益比例</p>
                                </div>
                            </li>
                        </ul>

                    </div>
                    <span class="border_bg_leftTop"></span>
                    <span class="border_bg_rightTop"></span>
                    <span class="border_bg_leftBottom"></span>
                    <span class="border_bg_rightBottom"></span>
                </div>
            </div>
        </div>
    </div>
    <div class="rightMain">
        <div class="rightMain_top">
            <div class="rightMain_topIn">
                <h3>数据情况统计</h3>
                <div class="biaoge"
                     style="width: 100%; height: 30vh; -webkit-tap-highlight-color: transparent; user-select: none; position: relative;"
                     id="chartmain_bing" _echarts_instance_="ec_1598945994795">
                    <div style="position: relative; width: 426px; height: 290px; padding: 0px; margin: 0px; border-width: 0px;">
                        <canvas data-zr-dom-id="zr_0" width="426" height="290"
                                style="position: absolute; left: 0px; top: 0px; width: 426px; height: 290px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); padding: 0px; margin: 0px; border-width: 0px;"></canvas>
                    </div>
                    <div></div>
                </div>
                <script type="text/javascript">
                    option = {
                        title: {
                            text: '数据情况统计',
                            subtext: '',
                            left: 'right',
                            textStyle: {
                                color: '#fff',
                                fontSize: 12
                            }
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: '{b} : {c} ({d}%)'
                        },
                        legend: {
                            // orient: 'vertical',
                            // top: 'middle',
                            type: 'scroll',
                            orient: 'vertical',
                            right: 10,
                            top: 40,
                            bottom: 20,
                            left: 'right',
                            data: ['AC', 'WA', 'TLE', 'OL', 'RE', 'PE', 'CE', 'SE'],
                            textStyle: {
                                color: '#fff',
                                fontSize: 12
                            }

                        },
                        grid: {
                            x: '-10%',
                            y: 40,
                            x2: 20,
                            y2: 20,
                        },
                        //color: ['#09d0fb', '#f88cfb', '#95f8fe', '#f9f390', '#ecfeb7', '#09d0fb', '#f88cfb', '#95f8fe', '#f9f390', '#ecfeb7'],
                        series: [
                            {
                                type: 'pie',
                                radius: '65%',
                                center: ['50%', '50%'],
                                selectedMode: 'single',
                                data: [
                                    <#list pies as map>
                                    {
                                        <#list map?keys as itemKey>

                                        <#if itemKey="name">
                                        'name': "${map[itemKey]}",
                                        </#if>
                                        <#if itemKey="value">
                                        'value': ${map[itemKey]}
                                        </#if>

                                        </#list>
                                    },
                                    </#list>
                                ],
                                emphasis: {
                                    itemStyle: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    };
                    //获取dom容器
                    const theme = 'light';
                    console.log('theme', theme);
                    var myChart = echarts.init(document.getElementById('chartmain_bing'), theme);
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);

                </script>
                <span class="border_bg_leftTop"></span>
                <span class="border_bg_rightTop"></span>
                <span class="border_bg_leftBottom"></span>
                <span class="border_bg_rightBottom"></span>
            </div>
        </div>
        <div class="rightMain_bottom">
            <div class="rightMain_bottomIn">
                <h3>排行榜(前20)</h3>
                <div class="biaoge biaoge_list" style="width:100%; height:36vh">
                    <div class="biaoge_listIn">
                        <ul class="ul_title">
                            <li>用户名</li>
                            <li>昵称</li>
                            <#--                            <li>专业</li>-->
                            <#--                            <li>班级</li>-->
                            <li>总提交量(行)</li>
                        </ul>
                        <div class="ul_list">
                            <div class="ul_listIn">
                                <#list ranklist as item>
                                    <ul class="ul_con">
                                        <li><#if item.userName??>${item.userName}</#if></li>
                                        <li><#if item.nickName??>${item.nickName}</#if></li>
                                        <#--                                    <li><#if item.major??>${item.major}</#if></li>-->
                                        <#--                                    <li><#if item.clazz??>${item.clazz}</#if></li>-->
                                        <li><#if item.codeLines??>${item.codeLines}</#if></li>
                                    </ul>
                                </#list>
                            </div>
                        </div>
                    </div>

                </div>
                <span class="border_bg_leftTop"></span>
                <span class="border_bg_rightTop"></span>
                <span class="border_bg_leftBottom"></span>
                <span class="border_bg_rightBottom"></span>
            </div>
        </div>
    </div>
    <#-- <div style="clear:both;"></div>-->
</div>
<!--数字增长累加动画-->
<script src="/loc/js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="/loc/js/jquery.numscroll.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    $(".shu1").numScroll();
    $(".shu2").numScroll();
    $(".shu3").numScroll();
    $(".shu4").numScroll();
    $(".shu5").numScroll();
    $(".shu6").numScroll();

    /*$(".num2").numScroll({
        time:5000
    });*/
</script>


</body>
</html>
