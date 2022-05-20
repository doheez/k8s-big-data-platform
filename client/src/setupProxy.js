const { createProxyMiddleware } = require('http-proxy-middleware');

//const hostLocation=process.env.REACT_APP_SERVER_HOST
const AWS_URL = 'ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com';
const LOCAL_URL = 'localhost';

module.exports = function (app) {
    app.use(
        createProxyMiddleware('/api', {
            target: `http://${AWS_URL}:8080`,
            changeOrigin: true
        })
    );
};