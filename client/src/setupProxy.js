const { createProxyMiddleware } = require('http-proxy-middleware');

//const hostLocation=process.env.REACT_APP_SERVER_HOST
const hostLocation='ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com';

module.exports = function (app) {
    app.use(
        createProxyMiddleware('/api', {
            target: `http://${hostLocation}:8080`,
            changeOrigin: true
        })
    );
};