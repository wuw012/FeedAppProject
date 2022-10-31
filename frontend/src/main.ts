import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import "./assets/main.css";
import { createAuth0 } from '@auth0/auth0-vue';
//import VueCookies from 'vue-cookies'

const app = createApp(App);

app.use(
  createAuth0({
    domain: "dev-qx7lu58v60ih68k8.us.auth0.com",
    client_id: "JFoEHXHjBmmbld5tAt1NypF427FskDfk",
    redirect_uri: window.location.origin
  })
//  createPinia,
//  router
);

//app.use(VueCookies);
app.use(createPinia());
app.use(router);


app.mount("#app");
