#### npm依赖安装记录

* Echarts依赖  
    npm install echarts -S
    
* QS依赖（解决axios的post请求数据转换问题）  
    npm install qs -S
```javascript
const Qs = require('qs')
let loginData = {
    'username': username,
    'password': password
}
request({
    url: '/user/login',
    method: 'post',
    //数据格式转换
    data: Qs.stringify(loginData)
})
```