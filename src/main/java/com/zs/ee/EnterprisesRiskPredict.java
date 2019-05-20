package com.zs.ee;

/**
 * 名称：预测预警类
 * 输入变量：
 * {
 * 来自数据库"DBO/pt_base_enterprise_monitor"表中的 共6个变量。
 * 1.时间: collect_time;
 * 2.企业ID: id;
 * 3.检测点名称 : en_name;
 * 4.监测点实时值: real_time_data;
 * 5.监测点高高报警线: uu_limit;
 * 6.监测点低低报警线:ll_limit;
 * 7.监测点高报警线: u_limit;
 * 8.监测点低报警线:l_limit;
 * }
 *
 * 输出变量：{
 * 9.监测点预测值（通过PredictSimple进行计算得出，其他信息均来自之前的表pt_base_enterprise_monitor复制而来）
 * }
 * 新建表1(监测点预测信息表)
 * |1.时间|2.企业ID|3.检测点名称|4.监测点实时值|5.监测点高高报警线|6.监测点低低报警线|7.监测点高报警线|8.监测点低报警线|9.监测点预测值|
 *
 * 输出变量：RISK:企业风险值;
 *
 * @author musk
 * 2019.05.13
 */
public class EnterprisesRiskPredict{


	public  double predictSimple(double collect_time,int id,double en_name,
								 double[] real_time_data,double uu_limit,double ll_limit,double u_limit,double l_limit,double En_Risk) {
		//需要输入一段时间内检测点历史数据 double[] real_time_data

		EnterprisesRiskPredict wp = new EnterprisesRiskPredict();

		double WpPredict= (double) wp.mathAve(real_time_data); //预测计算

		//显示

		//	System.out.println("-------------------------");
		//	System.out.println("监测点"+ id +"的预测值为"+" "+ WpPredict);

		double WpRisk= (double) wp.mathRisk(uu_limit, ll_limit, WpPredict)*En_Risk; //风险值计算

		//	System.out.println("该监测点的风险值为：" + WpRisk);
		return WpRisk;


	}
	/**
	 * 计算企业危险源风险值
	 * @return
	 */
	private  double  mathRisk(double UUP_limit,double LLow_limit,double predictValue){
		double RISK = 0;
		//判断预测值是否大于上限
		if(predictValue > UUP_limit){
			RISK = (double) ((predictValue - UUP_limit)/predictValue);
		}
		else if(predictValue < LLow_limit){
			RISK = (double) ((UUP_limit - predictValue  )/predictValue);
		}
		else RISK = 0;


		RISK = RISK*predictValue;
		return RISK;

	}
	//显示企业风险值信息
	static double riskDisplay(double Risk1,double Risk2){

		double CorRisk;

		CorRisk = Risk1 + Risk2;

		return CorRisk;
	}

	/**
	 * 计算下一时刻值监测点值
	 * @param
	 * @return
	 */
	private  float  mathAve(double[] ls){
		float num=0;
		for(int i=0;i<ls.length;i++){
			float a=(float) ls[i];
			num=num+a;
		}
		float ave=num/ls.length;
		return ave;
	}

}


