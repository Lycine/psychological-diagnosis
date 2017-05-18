package bayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * 贝叶斯主体类

 */
public class BayesImpl {
    /**
     * 将原训练元组按类别划分
     * @param datas 训练元组
     * @return Map<类别，属于该类别的训练元组>
     */
    Map<String, ArrayList<ArrayList<String>>> datasOfClass(ArrayList<ArrayList<String>> datas){
        Map<String, ArrayList<ArrayList<String>>> map = new HashMap<String, ArrayList<ArrayList<String>>>();
        ArrayList<String> t = null;
        String c = "";
        for (int i = 0; i < datas.size(); i++) {
            t = datas.get(i);
            c = t.get(t.size() - 1);
            if (map.containsKey(c)) {
                map.get(c).add(t);
            } else {
                ArrayList<ArrayList<String>> nt = new ArrayList<ArrayList<String>>();
                nt.add(t);
                map.put(c, nt);
            }
        }
        return map;
    }

    /**
     * 在训练数据的基础上预测测试元组的类别
     * @param datas 训练元组
     * @param testT 测试元组
     * @return 测试元组的类别
     */
    public HashMap<String, String> predictClass(ArrayList<ArrayList<String>> datas, ArrayList<String> testT) {
        HashMap<String, String> result = new HashMap<String, String>();
        Map<String, ArrayList<ArrayList<String>>> doc = this.datasOfClass(datas);
        Object classes[] = doc.keySet().toArray();
        double maxP = 0.00;
        int maxPIndex = -1;
        for (int i = 0; i < doc.size(); i++) {
            String c = classes[i].toString();
            ArrayList<ArrayList<String>> d = doc.get(c);
            double pOfC = DecimalCalculate.div(d.size(), datas.size(), 3);
            for (int j = 0; j < testT.size(); j++) {
                double pv = this.pOfV(d, testT.get(j), j);
                pOfC = DecimalCalculate.mul(pOfC, pv);
            }
            if(pOfC > maxP){
                maxP = pOfC;
                maxPIndex = i;
            }
        }

        try {
            System.out.println(classes[maxPIndex].toString());
            result.put("status","success");
            result.put("label",classes[maxPIndex].toString());
        } catch (ArrayIndexOutOfBoundsException e){
            result.put("status","failed");
            result.put("label","诊断失败，错误原因可能是病症描述太少");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 计算指定属性列上指定值出现的概率
     * @param d 属于某一类的训练元组
     * @param value 列值
     * @param index 属性列索引
     * @return 概率
     */
    private double pOfV(ArrayList<ArrayList<String>> d, String value, int index) {
        double p = 0.00;
        int count = 0;
        int total = d.size();
        ArrayList<String> t = null;
        for (int i = 0; i < total; i++) {
            if(d.get(i).get(index).equals(value)){
                count++;
            }
        }
        p = DecimalCalculate.div(count, total, 3);
        return p;
    }
} 