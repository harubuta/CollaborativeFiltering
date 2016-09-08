

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class RecommendEngine {


	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		// 過去の渡航者データ
	    String[] prodname = {"TPE", "BKK", "HNL", "GUM"};
	    double[][] salesData = {
	      {2, 1, 0, 0},
	      {1, 0, 0, 0},
	      {0, 0, 3, 2},
	      {2, 3, 1, 0},
	      {1, 0, 2, 5},
	    };

	 // 相関係数行列の計算
	    ComputeCorrelationMatrix CCM = new ComputeCorrelationMatrix();
	    RealMatrix corrMatrix = CCM.computeCorrelationMatrix(salesData);
	    System.out.println("相関係数行列: " + corrMatrix);

	    // リコメンドの実施
	    double[][] userSales = {{0, 1, 5, 0}};
	    RealMatrix userSalesMatrix = MatrixUtils.createRealMatrix(userSales);
	    RealMatrix scores = userSalesMatrix.multiply(corrMatrix);

	    for(int i = 0; i < prodname.length; i++) {
	    	System.out.println( prodname[i] + ":" + scores.getEntry(0, i));
	    }
	}

}
