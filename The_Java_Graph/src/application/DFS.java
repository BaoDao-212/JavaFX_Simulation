package application;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class DFS extends CanvasController {
	int time = DefineGraph.cref.time;
	SequentialTransition st = DefineGraph.cref.st;

	public DFS(Node source) {
		// set khoang cach cho nut nguon
		source.minDistance = 0;
		source.visited = true;
		DFSRecursion(source, 0);

		// chay xong thuat toan thì set lai màu cho tat ca 
		st.setOnFinished(ev -> {
			for (NodeFX n : DefineGraph.cref.circles) {
				FillTransition ft1 = new FillTransition(Duration.millis(time), n);
				ft1.setToValue(Color.BLACK);
				ft1.play();
			}
			if (DefineGraph.cref.directed) {
				for (Shape n : DefineGraph.cref.edges) {
					n.setFill(Color.BLACK);
				}
			} else if (DefineGraph.cref.undirected) {
				for (Shape n : DefineGraph.cref.edges) {
					n.setStroke(Color.BLACK);
				}
			}
			FillTransition ft1 = new FillTransition(Duration.millis(time), source.circle);
			ft1.setToValue(Color.RED);
			ft1.play();
			// sau khi ket thuc thuat toan chuyen nut playpause sang trang thai play 
			Image image = new Image(getClass().getResourceAsStream("/image/hiPlay.png"));
			DefineGraph.cref.getPlayPauseImage().setImage(image);
			DefineGraph.cref.paused = true;
			DefineGraph.cref.playing = false;
			textFlow.appendText("---Finished--\n");
		});
		st.onFinishedProperty();
		st.play();
		DefineGraph.cref.playing = true;
		DefineGraph.cref.paused = false;
	}

	public void DFSRecursion(Node source, int level) {

		// set animation cho cac nut sang mau xanh tru nut  duoc di qua
		FillTransition ft = new FillTransition(Duration.millis(time), source.circle);
		if (source.circle.getFill() == Color.BLACK) {
			ft.setToValue(Color.FORESTGREEN);
		}
		st.getChildren().add(ft);
		// add các nút vào string
		String str = "";
		for (int i = 0; i < level; i++) {
			str = str.concat("\t");
		}
		str = str+"DFS(" + source.name + ") Enter\n";
		// add thong tin trong qua trinh chay theo chieu sau
		final String str2 = str;
		FadeTransition fd = new FadeTransition(Duration.millis(10), textFlow);
		fd.setOnFinished(e -> {
			textFlow.appendText(str2);
		});
		fd.onFinishedProperty();
		st.getChildren().add(fd);
		// duyet xem cac canh ke voi nut source
		for (Edge e : source.adjacents) {
			if (e != null) {
				Node v = e.target;
				// kiem tra xem no da duoc di qua chua
				if (!v.visited) {
					v.minDistance = source.minDistance + 1;
					v.visited = true;
					v.previous = source;// gan nut truoc cua nut source la previous
                   v.circle.distance.setText("Dist. : " + v.minDistance);
					// set animation cho cung duoc di qua sang mau xanh
					if (DefineGraph.undirected) {
						StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), e.line);
						ftEdge.setToValue(Color.FORESTGREEN);
						st.getChildren().add(ftEdge);
					} else if (DefineGraph.directed) {
						FillTransition ftEdge = new FillTransition(Duration.millis(time), e.line);
						ftEdge.setToValue(Color.FORESTGREEN);
						st.getChildren().add(ftEdge);
					}
					// goi de quy de thuc hien duyet sau
					DFSRecursion(v, level + 1);
					// set animation cua cung sang mau tim xanh sau khi de quy goi toi nut la cua 1 nhanh
					if (DefineGraph.undirected) {
						StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), e.line);
						ftEdge.setToValue(Color.BLUEVIOLET);
						st.getChildren().add(ftEdge);
					} else if (DefineGraph.directed) {
						FillTransition ftEdge = new FillTransition(Duration.millis(time), e.line);
						ftEdge.setToValue(Color.BLUEVIOLET);
						st.getChildren().add(ftEdge);
					}
				//set animation cua nut vua duoc sau khi de quy goi toi nut la cua 1 nhanh
					FillTransition ft1 = new FillTransition(Duration.millis(time), v.circle);
					ft1.setToValue(Color.BLUEVIOLET);
					ft1.onFinishedProperty();
					ft1.setOnFinished(ev -> {
						v.circle.distance.setText("Dist. : " + v.minDistance);
					});
					st.getChildren().add(ft1);
					
				}
			}
		}
		// khi toi cac nut la(ngon)
		str = "";
		for (int i = 0; i < level; i++) {
			str = str.concat("\t");
		}
		str = str.concat("DFS(" + source.name + ") Exit\n");
		final String str1 = str;
		fd = new FadeTransition(Duration.millis(10), textFlow);
		fd.setOnFinished(e -> {
			textFlow.appendText(str1);
		});
		fd.onFinishedProperty();
		st.getChildren().add(fd);
	}
}
