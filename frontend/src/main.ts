import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "@/router/index.js";

import "./assets/main.css";
import 'bootstrap/dist/css/bootstrap.css';

//import VueCookies from 'vue-cookies'

const app = createApp(App);

app.use(createPinia());
app.use(router);


app.mount("#app");


import 'bootstrap/dist/js/bootstrap.js';