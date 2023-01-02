import { createApp } from "vue";
import { createPinia } from "pinia";
import { Notify, Quasar } from "quasar";
import App from "./App.vue";
import router from "./router";

import "@quasar/extras/material-icons/material-icons.css";
import "quasar/src/css/index.sass";
import "@quasar/extras/roboto-font/roboto-font.css";
import "./assets/main.css";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(Quasar, {
  plugins: { Notify }, // import Quasar plugins and add here
});
app.mount("#app");
