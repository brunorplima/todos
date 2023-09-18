import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import 'bootstrap/dist/css/bootstrap.css'
import startIconLibrary from "./assets/icons/iconLibrary.js";
import router from "./router.js";

const { componentName, FontAwesomeIcon } = startIconLibrary()

createApp(App)
  .component(componentName, FontAwesomeIcon)
  .use(router)
  .mount('#app')
