<!DOCTYPE html>
<!-- saved from url=(0056)http://www.17sucai.com/preview/1/2017-09-04/hj/demo.html -->
<html class="forhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>OJ 提交数据统计</title>
<link type="text/css" rel="stylesheet" href="/loc/css/style.css">
    <style>
        a {
            text-decoration: none;
            color: #666;
            -webkit-tap-highlight-color: rgba(0,0,0,0);
            -webkit-font-smoothing: antialiased;
            display: inline-block;
            vertical-align: middle;
            -webkit-transform: perspective(1px) translateZ(0);
            transform: perspective(1px) translateZ(0);
            box-shadow: 0 0 1px rgba(0, 0, 0, 0);
            position: relative;
            -webkit-transition-property: color;
            transition-property: color;
            -webkit-transition-duration: 0.3s;
            transition-duration: 0.3s;
        }


    </style>
</head>

<body>
<section class="title">
    <p>年提交量数据统计<sup style="font-size:20px">&nbsp;(单位: 行)</sup></p>
</section>

<#list step as i>
<section class="cards">
    <#if i != step[step?size-1]>
        <#assign begin = 4>
    <#else >
        <script>
            console.log('size : '+ ${data?size-1} + ' i: '+ ${i})
        </script>
        <#assign begin = (data?size-1) - i-1>
    </#if>
    <script>
        console.log(${begin})
    </script>
    <#list 0..begin as v>
    <#if v % 5 == 0>
    <div class="card card--oil">
        <div class="card__svg-container">
            <div class="card__svg-wrapper">
                <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 80 80">
                    <filter id="goo">
                        <fegaussianblur in="SourceGraphic" stdDeviation="10" result="blur"></fegaussianblur>
                        <fecolormatrix in="blur" mode="matrix" values="1 0 0 0 0  0 1 0 0 0  0 0 1 0 0  0 0 0 19 -9" result="goo"></fecolormatrix>
                        <feblend in="SourceGraphic" in2="goo"></feblend>
                    </filter>
                    <circle cx="40" cy="40" r="39" fill="#6a7a87"></circle>
                    <g filter="url(#goo)">
                        <path id="myTeardrop1" fill="#FFFFFF" d="M48.9,43.6c0,4.9-4,8.9-8.9,8.9s-8.9-4-8.9-8.9S40,27.5,40,27.5S48.9,38.7,48.9,43.6z" data-svg-origin="31.100000381469727 27.5" style="transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 2.68382, 0, 1);"></path>
                        <path id="TopInit1" fill="#FFFFFF" d="M13,10.8c5-5.3,10.7-8.5,18.3-9.8c11.2-1.8,9.2-1.4,17.6,0C58.3,2.7,66,6,69,13.1V-2.7L13-2.8V10.8z"></path>
                        <path id="TopBulb1" fill="#FFFFFF" d="M13,10.8c5-5.3,14.8-4,18.3,2.3c4.3,7.7,13.8,7.6,17.6,0c3.4-7,17.1-7.1,20.1,0V-2.7L13-2.8V10.8z" style="visibility: hidden"></path>
                        <path id="TopBulbSm1" fill="#FFFFFF" d="M13,10.8c5-5.3,18.5-14,23.3-8.8c3.6,3.9,3.9,4.5,7.6,0c5-6,22.1,3.9,25.1,11V-2.7L13-2.8V10.8z" style="visibility: hidden"></path>
                        <path id="TopRound1" fill="#FFFFFF" d="M13,10.8c5-5.3,10.6-6,18.3-6.8c6.5-0.7,10.5-0.8,17.6,0C58.4,5.1,66,6,69,13.1V-2.7L13-2.8V10.8z" style="visibility: hidden"></path>
                    </g>
                </svg>
            </div>
        </div>
        <div class="card__count-container">
            <div class="card__count-text">
                <!-- freemarker 转换为数字后除以10000 -->
                <span class="card__count-text--big">${data[i+v]?number/10000}</span> 万
            </div>
        </div>
        <div class="card__stuff-container">
            <div class="card__stuff-text"><a href="./year/2009"> ${dataAxis[i+v]}</a></div>
        </div>
    </div>
    </#if>

    <#if v % 5 == 1>
    <div class="card card--tree">
        <div class="card__svg-container">
            <div class="card__svg-wrapper">
                <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 80 80">
                    <circle cx="40" cy="40" r="39" fill="#6abf60"></circle>
                    <g id="Branches" data-svg-origin="52 54.29999923706055" style="transform-origin: 0px 0px 0px; transform: matrix(1, 0, 0, 1, 0, 0);">
                        <polygon id="topBranches" fill="#FFFFFF" points="40.1,19.8 51.2,43.1 29,43.1" data-svg-origin="40.10000038146973 43.099998474121094" style="transform-origin: 0px 0px 0px; transform: matrix(1, 0, 0, 1, 0, 0);"></polygon>
                        <polygon id="botBranches" fill="#FFFFFF" points="40,28 52,54.3 28,54.3" data-svg-origin="40 54.29999923706055" style="transform-origin: 0px 0px 0px; transform: matrix(1, 0, 0, 1, 0, 0);"></polygon>
                    </g>
                    <rect id="Trunk" x="37.7" y="53.8" fill="#FFFFFF" width="4.7" height="6" data-svg-origin="40.05000066757202 59.79999923706055" style="transform-origin: 0px 0px 0px; transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);"></rect>
                    <rect id="Particle" x="37.9" y="54.3" fill="#FFFFFF" width="2" height="2" data-svg-origin="37.900001525878906 54.29999923706055" style="transform: matrix(1, 0, 0, 1, -10, 4); visibility: hidden; opacity: 0;"></rect>
                    <polygon id="Axe" fill="#FFFFFF" points="0.7,5.3 7.3,5.3 7.3,10.2 4,20.3 0.7,10.2" data-svg-origin="4.000000178813934 12.799999713897705" style="transform-origin: 0px 0px 0px; transform: matrix(0.04696, -0.99889, 0.99889, 0.04696, -18.9737, 59.1944);"></polygon>
                </svg>
            </div>
        </div>
        <div class="card__count-container">
            <div class="card__count-text">
                <span class="card__count-text--big">${data[i+v]?number/10000}</span> 万
            </div>
        </div>
        <div class="card__stuff-container">
            <div class="card__stuff-text"> <a href="./year/2010">  ${dataAxis[i+v]}</a></div>
        </div>
    </div>
    </#if>

    <#if v % 5 == 2>
    <div class="card card--oil">
        <div class="card__svg-container">
            <div class="card__svg-wrapper">
                <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 80 80">
                    <filter id="goo">
                        <fegaussianblur in="SourceGraphic" stdDeviation="10" result="blur"></fegaussianblur>
                        <fecolormatrix in="blur" mode="matrix" values="1 0 0 0 0  0 1 0 0 0  0 0 1 0 0  0 0 0 19 -9" result="goo"></fecolormatrix>
                        <feblend in="SourceGraphic" in2="goo"></feblend>
                    </filter>
                    <circle cx="40" cy="40" r="39" fill="#6a7a87"></circle>
                    <g filter="url(#goo)">
                        <path id="myTeardrop" fill="#FFFFFF" d="M48.9,43.6c0,4.9-4,8.9-8.9,8.9s-8.9-4-8.9-8.9S40,27.5,40,27.5S48.9,38.7,48.9,43.6z" data-svg-origin="31.100000381469727 27.5" style="transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 2.68382, 0, 1);"></path>
                        <path id="TopInit" fill="#FFFFFF" d="M13,10.8c5-5.3,10.7-8.5,18.3-9.8c11.2-1.8,9.2-1.4,17.6,0C58.3,2.7,66,6,69,13.1V-2.7L13-2.8V10.8z"></path>
                        <path id="TopBulb" fill="#FFFFFF" d="M13,10.8c5-5.3,14.8-4,18.3,2.3c4.3,7.7,13.8,7.6,17.6,0c3.4-7,17.1-7.1,20.1,0V-2.7L13-2.8V10.8z" style="visibility: hidden"></path>
                        <path id="TopBulbSm" fill="#FFFFFF" d="M13,10.8c5-5.3,18.5-14,23.3-8.8c3.6,3.9,3.9,4.5,7.6,0c5-6,22.1,3.9,25.1,11V-2.7L13-2.8V10.8z" style="visibility: hidden"></path>
                        <path id="TopRound" fill="#FFFFFF" d="M13,10.8c5-5.3,10.6-6,18.3-6.8c6.5-0.7,10.5-0.8,17.6,0C58.4,5.1,66,6,69,13.1V-2.7L13-2.8V10.8z" style="visibility: hidden"></path>
                    </g>
                </svg>
            </div>
        </div>
        <div class="card__count-container">
            <div class="card__count-text">
                <span class="card__count-text--big">${data[i+v]?number/10000}</span> 万
            </div>
        </div>
        <div class="card__stuff-container">
            <div class="card__stuff-text"><a href="./year/2011">   ${dataAxis[i+v]}</a></div>
        </div>
    </div>
    </#if>

    <#if v % 5 == 3>
    <div class="card card--water">
        <div class="card__svg-container">
            <div class="card__svg-wrapper">
                <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 80 80">
                    <circle cx="40" cy="40" r="39" fill="#60cbe7"></circle>
                    <g id="waveGroup" data-svg-origin="36.599998474121094 39.949997901916504" style="transform-origin: 0px 0px 0px; transform: matrix(1, 0, 0, 1, 0, 0);">
                        <path id="waveTop" fill="#FFFFFF" d="M93,34.1c-3.5,0-5.8-1.1-8.1-4.1h0c-1.6,3-4.9,4.3-8.4,4.3c-3.5,0-6.1-1.3-8.4-4.3h0c-1.6,3-5.1,4.1-8.6,4.1
	c-3.5,0-6.6-2-8.6-4.6v0c-2,2.6-4.5,4.3-8,4.3c-3.5,0-6-1.7-8-4.3v0c-2,2.6-5.1,4.5-8.6,4.5c-3.5,0-6.3-1.1-8.5-4.1h0
	c-1.6,3-4.9,4.3-8.4,4.3C6,34.3,3.3,33,1.1,30h0c-1.6,3-4.5,4.1-8,4.1c-3.5,0-6-2-8-4.6v0c-2,2.6-5.5,4.3-9,4.3c-3.5,0-6-1.7-9-4.3
	v6.6c3,1.5,5.6,2.3,8.6,2.3s6.2-0.9,8.5-2.3c2.2,1.5,5.4,2.3,8.5,2.3s6.1-0.9,8.4-2.3c2.2,1.5,5.4,2.3,8.4,2.3s6.1-0.9,8.4-2.3
	c2.2,1.5,5.4,2.3,8.4,2.3s6.1-0.9,8.4-2.3c2.2,1.5,5.3,2.3,8.4,2.3s6.1-0.9,8.4-2.3c2.2,1.5,5.3,2.3,8.4,2.3s6.1-0.9,8.4-2.3
	c2.2,1.5,5.3,2.3,8.4,2.3s6.1-0.9,8.4-2.3c2.2,1.5,5,2.3,8,2.3s6-0.9,8-2.3v-6.6C100,32.1,96.5,34.1,93,34.1z"></path>
                        <path id="waveBot" fill="#FFFFFF" d="M98,46.1c-3.5,0-5.8-1.1-8.1-4.1h0c-1.6,3-4.9,4.3-8.4,4.3c-3.5,0-6.1-1.3-8.4-4.3h0c-1.6,3-5.1,4.1-8.6,4.1
	c-3.5,0-6.6-2-8.6-4.6v0c-2,2.6-4.5,4.3-8,4.3c-3.5,0-6-1.7-8-4.3v0c-2,2.6-5.1,4.5-8.6,4.5c-3.5,0-6.3-1.1-8.5-4.1h0
	c-1.6,3-4.9,4.3-8.4,4.3C11,46.3,8.3,45,6.1,42h0c-1.6,3-4.5,4.1-8,4.1c-3.5,0-6-2-8-4.6v0c-2,2.6-5.5,4.3-9,4.3c-3.5,0-6-1.7-9-4.3
	v6.6c3,1.5,5.6,2.3,8.6,2.3s6.2-0.9,8.5-2.3c2.2,1.5,5.4,2.3,8.5,2.3s6.1-0.9,8.4-2.3c2.2,1.5,5.4,2.3,8.4,2.3s6.1-0.9,8.4-2.3
	c2.2,1.5,5.4,2.3,8.4,2.3c3,0,6.1-0.9,8.4-2.3c2.2,1.5,5.3,2.3,8.4,2.3s6.1-0.9,8.4-2.3c2.2,1.5,5.3,2.3,8.4,2.3
	c3,0,6.1-0.9,8.4-2.3c2.2,1.5,5.3,2.3,8.4,2.3s6.1-0.9,8.4-2.3c2.2,1.5,5,2.3,8,2.3s6-0.9,8-2.3v-6.6C105,44.1,101.5,46.1,98,46.1z"></path>
                    </g>
                </svg>
            </div>
        </div>
        <div class="card__count-container">
            <div class="card__count-text">
                <span class="card__count-text--big">${data[i+v]?number/10000}</span> 万
            </div>
        </div>
        <div class="card__stuff-container">
            <div class="card__stuff-text"><a href="./year/2012">   ${dataAxis[i+v]}</a></div>
        </div>
    </div>
    </#if>

    <#if v % 5 == 4>
    <div class="card card--oil">
        <div class="card__svg-container">
            <div class="card__svg-wrapper">
                <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 80 80">
                    <filter id="goo">
                        <fegaussianblur in="SourceGraphic" stdDeviation="10" result="blur"></fegaussianblur>
                        <fecolormatrix in="blur" mode="matrix" values="1 0 0 0 0  0 1 0 0 0  0 0 1 0 0  0 0 0 19 -9" result="goo"></fecolormatrix>
                        <feblend in="SourceGraphic" in2="goo"></feblend>
                    </filter>
                    <circle cx="40" cy="40" r="39" fill="#6a7a87"></circle>
                    <g filter="url(#goo)">
                        <path id="myTeardrop" fill="#FFFFFF" d="M48.9,43.6c0,4.9-4,8.9-8.9,8.9s-8.9-4-8.9-8.9S40,27.5,40,27.5S48.9,38.7,48.9,43.6z" data-svg-origin="31.100000381469727 27.5" style="transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 2.68382, 0, 1);"></path>
                        <path id="TopInit" fill="#FFFFFF" d="M13,10.8c5-5.3,10.7-8.5,18.3-9.8c11.2-1.8,9.2-1.4,17.6,0C58.3,2.7,66,6,69,13.1V-2.7L13-2.8V10.8z"></path>
                        <path id="TopBulb" fill="#FFFFFF" d="M13,10.8c5-5.3,14.8-4,18.3,2.3c4.3,7.7,13.8,7.6,17.6,0c3.4-7,17.1-7.1,20.1,0V-2.7L13-2.8V10.8z" style="visibility: hidden"></path>
                        <path id="TopBulbSm" fill="#FFFFFF" d="M13,10.8c5-5.3,18.5-14,23.3-8.8c3.6,3.9,3.9,4.5,7.6,0c5-6,22.1,3.9,25.1,11V-2.7L13-2.8V10.8z" style="visibility: hidden"></path>
                        <path id="TopRound" fill="#FFFFFF" d="M13,10.8c5-5.3,10.6-6,18.3-6.8c6.5-0.7,10.5-0.8,17.6,0C58.4,5.1,66,6,69,13.1V-2.7L13-2.8V10.8z" style="visibility: hidden"></path>
                    </g>
                </svg>
            </div>
        </div>
        <div class="card__count-container">
            <div class="card__count-text">
                <span class="card__count-text--big">${data[i+v]?number/10000}</span> 万
            </div>
        </div>
        <div class="card__stuff-container">
            <div class="card__stuff-text"><a href="./year/2013">  2013</a></div>
        </div>
    </div>
    </#if>
    </#list>
    </section>
</#list>
<script type="text/javascript" src="/loc/js/jquery.min.js"></script>
<script type="text/javascript" src="/loc/js/TweenMax.min.js"></script>

</body></html>
