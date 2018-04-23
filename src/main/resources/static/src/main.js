import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueRouter from 'vue-router'
import App from './App.vue'

import test from './component/Test.vue'

Vue.use(ElementUI)
Vue.use(VueRouter)

let router = new VueRouter({
    mode: 'history',
    routes: [
        {path: '/test', name: 'test', component: test}
    ]
})


new Vue({
    router,
    el: '#app',
    render: h => h(App)
})
