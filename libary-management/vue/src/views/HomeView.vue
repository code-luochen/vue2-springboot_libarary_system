<template>
  <div>
    <div style="margin:20px 0">
      <el-select class="input" v-model="timeRange" placeholder="请选择" @change="load">

        <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value" 
        >
        </el-option>

      </el-select>
    </div>

    <el-card>
      <div id="line"></div>
    </el-card>
  </div>
</template>

<script>
import Cookies from "js-cookie"
import * as echarts from "echarts"

const option = {
  title: {
    text: '图书借还统计'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['借书数量','还书数量']
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['周一','周二']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '借书数量',
      type: 'line',
      stack: 'Total',
      data: [1,2]
    },
    {
      name: '还书数量',
      type: 'line',
      stack: 'Total',
      data: [2,1]
    }
  ]
};

export default {
  name: 'HomeView',
  components: {
  },
  data(){
    return{
      admin:Cookies.get("admin") ? JSON.parse(Cookies.get('admin')) : {},
      lineBox:null,
      timeRange:'week',
      options:[
        {label:'最近一周',value:'week'},
        {label:'最近一个月',value:'month'},
        {label:'最近两个月',value:'month2'},
        {label:'最近三个月',value:'month3'},
      ]
    }
  },
  mounted(){
    this.load()
  },
  methods:{
    load(){
      if(!this.lineBox){
        this.lineBox = echarts.init(document.getElementById("line"))
      }


      this.lineBox.setOption(option)
    }
  }
  
 
}
</script>

<style lang="less" scoped>
.item{
  margin-top: 10px;
  color:#666;
}
#line{
  width: 100%;
  height: 400px;
}
</style>
