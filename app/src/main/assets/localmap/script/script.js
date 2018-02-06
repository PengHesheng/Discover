 /*将页面分为10rem*/
 	document.getElementsByTagName("html")[0].style.fontSize = document.documentElement.clientWidth/10 + "px";

    

    
    //获取容器

    var map = new BMap.Map("contain");          // 创建地图实例  
    var point = new BMap.Point(106.559829,29.684496);  // 创建点坐标  
    map.centerAndZoom(point,5);
    

    var point2 = new BMap.Point(106.559829,29.684496);  // 创建点坐标

    //调用路线

    map.addEventListener("dbclick",function(){
        var driving = new BMap.DrivingRoute("重庆", {    
        renderOptions: {
            map   : map,     
            panel : "results",    
            autoViewport: true    
          }    
        });    
        driving.search(point,point2);
    })

    
    var data=[{"name":"枫香秋停景区","x":106.559829,"y":29.684496},
              {"x":106.595956,"y":29.657149},
              {"x":106.780327,"y":29.77305},
              {"x":106.519386,"y":29.397193},
              {"x":106.691166,"y":29.220831}
            ];


       




    for(var i=0;i < data.length;i++){
        
        //标注
        var point = new BMap.Point(data[i].x,data[i].y);
        var myIcon = new BMap.Icon("u53.png", new BMap.Size(20, 20), {
        anchor: new BMap.Size(10, 25),
        });      
        // 创建标注对象并添加到地图   
        var marker = new BMap.Marker(point, {icon: myIcon});    
        map.addOverlay(marker);
        
    
    

    
     


    	
    	
    	//窗口
    	var opts = {    
            width : 0,     // 信息窗口宽度    
            height: 0,   // 信息窗口高度

        } 

        var con = "<div style=\"width:190px;position:absolute;height:130px;\"><img style=\"width:50px;height:50px;position:relative;left:3px;top:5px;border-radius:50%\" src=\"u5.png\"><div style=\"width:133px;overflow:hidden;display:table-cell;vertical-align:middle;font-size:8px;height:30px;left:57px;top:-54px;position:relative;\">南山一棵树观景台</div><div style=\"width:133px;overflow:hidden;font-size:8px;height:30px;left:57px;top:-54px;line-height:30px;position:relative;\">重庆市南岸区崇文路2号</div><img style=\"width:190px;height:70px;position:relative;left:0px;top:-60px\" src=\"u5.png\"></div>";

    	var infoWindow = new BMap.InfoWindow(con,opts); //添加提示信息
        marker.addEventListener("mouseover", function () { this.openInfoWindow(infoWindow); });  //添加事件       
        map.addOverlay(marker);
    }
    
       
 
 

    



    map.enableScrollWheelZoom(true);  

    var myStyleJson=[{
                "featureType": "water",
                "elementType": "all",
                "stylers": {
                    "color": "#044161"
                }
            }, {
                "featureType": "land",
                "elementType": "all",
                "stylers": {
                    "color": "#091934"
                }
            }, {
                "featureType": "boundary",
                "elementType": "geometry",
                "stylers": {
                    "color": "#064f85"
                }
            }, {
                "featureType": "railway",
                "elementType": "all",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "highway",
                "elementType": "geometry",
                "stylers": {
                    "color": "#004981"
                }
            }, {
                "featureType": "highway",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#005b96",
                    "lightness": 1
                }
            }, {
                "featureType": "highway",
                "elementType": "labels",
                "stylers": {
                    "visibility": "on"
                }
            }, {
                "featureType": "arterial",
                "elementType": "geometry",
                "stylers": {
                    "color": "#004981",
                    "lightness": -39
                }
            }, {
                "featureType": "arterial",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#00508b"
                }
            }, {
                "featureType": "poi",
                "elementType": "all",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "green",
                "elementType": "all",
                "stylers": {
                    "color": "#056197",
                    "visibility": "off"
                }
            }, {
                "featureType": "subway",
                "elementType": "all",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "manmade",
                "elementType": "all",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "local",
                "elementType": "all",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "arterial",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "boundary",
                "elementType": "geometry.fill",
                "stylers": {
                    "color": "#029fd4"
                }
            }, {
                "featureType": "building",
                "elementType": "all",
                "stylers": {
                    "color": "#1a5787"
                }
            }, {
                "featureType": "label",
                "elementType": "all",
                "stylers": {
                    "visibility": "off"
                }
            }, {
                "featureType": "poi",
                "elementType": "labels.text.fill",
                "stylers": {
                    "color": "#ffffff"
                }
            }, {
                "featureType": "poi",
                "elementType": "labels.text.stroke",
                "stylers": {
                    "color": "#1e1c1c"
                }
            }, {
                "featureType": "administrative",
                "elementType": "labels",
                "stylers": {
                    "visibility": "on"
                }
            },{
                "featureType": "road",
                "elementType": "labels",
                "stylers": {
                    "visibility": "off"
                }
            }];
    map.setMapStyle({styleJson: myStyleJson }); 


