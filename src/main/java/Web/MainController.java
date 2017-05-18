package Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tfidf.TFIDF4Files;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private PassageService passageService;

    @RequestMapping(value = "/")
    public String home(Model model, HttpSession session, HttpServletRequest request) {
        Passage passage = new Passage();
        passage.setId(1);
        passage = passageService.findPassageById(passage);
        System.out.println("passage: " + passage);
        return "detail";
    }

    @RequestMapping(value = "/getSymptom", produces = "application/json")
    @ResponseBody
    public Object getSymptom(String symptom, ServletRequest req, ServletResponse res) {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        System.out.println("symptom: " + symptom);

        HashMap<String, String> result = new HashMap<>();

        // TODO Auto-generated method stub

        TFIDF4Files tfidf4Files = new TFIDF4Files();
//            打war包前
//            String file = "data4tfidf";

//            打war包后
        String file = "/Users/hongyu/Desktop/psy-data-set";
//        String file = "/home/psy/psy-data-set";
        HashMap<String, HashMap<String, Float>> all_tf = null;
        try {
            all_tf = tfidf4Files.tfAllFiles(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println();
        HashMap<String, Float> idfs = tfidf4Files.idf(all_tf);
//        System.out.println();
        float rate = (float) 0.2;
        List<String> list = tfidf4Files.tf_idf(all_tf, idfs, rate);
//      打war包前
//      String trainFile = "data/data.txt";

//      打war包后
        String trainFile = "/Users/hongyu/Desktop/psy-data-set/data.txt";
//        String trainFile = "/home/psy/psy-data-set/data.txt";

        try {
            ArrayList<ArrayList<String>> data = tfidf4Files.getTrainData(trainFile, list, "utf-8");

            String content = symptom;

            ArrayList<String> test = tfidf4Files.getTestData(list, content);

            bayes.BayesImpl bayes = new bayes.BayesImpl();

            HashMap<String, String> bayesPredictClassResult = bayes.predictClass(data, test);
            if ("success".equals(bayesPredictClassResult.get("status"))) {
                result.put("status", "success");
                result.put("content", bayesPredictClassResult.get("label"));
            } else {
                result.put("status", "error");
                result.put("content", bayesPredictClassResult.get("label"));
            }
            System.out.println("label: " + bayesPredictClassResult.get("label"));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("content", "诊断失败，未知错误");
        }
        return result;
    }

    @RequestMapping(value = "/getSymptomHTML")
    public String getSymptomHTML(Model model,String symptom, ServletRequest req, ServletResponse res) {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        System.out.println("symptom: " + symptom);

        HashMap<String, String> result = new HashMap<>();

        // TODO Auto-generated method stub

        TFIDF4Files tfidf4Files = new TFIDF4Files();
//            打war包前
//            String file = "data4tfidf";

//            打war包后
        String file = "/Users/hongyu/Desktop/psy-data-set";
//        String file = "/home/psy/psy-data-set";
        HashMap<String, HashMap<String, Float>> all_tf = null;
        try {
            all_tf = tfidf4Files.tfAllFiles(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println();
        HashMap<String, Float> idfs = tfidf4Files.idf(all_tf);
//        System.out.println();
        float rate = (float) 0.2;
        List<String> list = tfidf4Files.tf_idf(all_tf, idfs, rate);
//      打war包前
//      String trainFile = "data/data.txt";

//      打war包后
        String trainFile = "/Users/hongyu/Desktop/psy-data-set/data.txt";
//        String trainFile = "/home/psy/psy-data-set/data.txt";

        try {
            ArrayList<ArrayList<String>> data = tfidf4Files.getTrainData(trainFile, list, "utf-8");

            String content = symptom;

            ArrayList<String> test = tfidf4Files.getTestData(list, content);

            bayes.BayesImpl bayes = new bayes.BayesImpl();

            HashMap<String, String> bayesPredictClassResult = bayes.predictClass(data, test);
            if ("success".equals(bayesPredictClassResult.get("status"))) {
                result.put("status", "success");
                result.put("content", bayesPredictClassResult.get("label"));
            } else {
                result.put("status", "error");
                result.put("content", bayesPredictClassResult.get("label"));
            }
            System.out.println("label: " + bayesPredictClassResult.get("label"));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("content", "诊断失败，未知错误");
        }
        Passage passage = new Passage();
        passage.setTitle(result.get("content"));
        System.out.println("passage2:" + passage.toString());
        passage = passageService.findPassageByTitle(passage);
        if (passage!=null) {
            System.out.println("find passage");
            System.out.println("passage1:" + passage.toString());
            model.addAttribute("passage",passage);
        }
        model.addAttribute("symptom",symptom);
        model.addAttribute("result", result);
        return "detail";
    }
}
