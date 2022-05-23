import React from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import axios from 'axios';

const container = document.getElementById('root');
const root = createRoot(container);

// 자격 증명을 사용하여 사이트 간 액세스 제어 요청을 해야 하는지 여부를 나타낸다. (기본값: false)
axios.defaults.withCredentials = true;

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
