package application;

import java.util.PriorityQueue;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class Dijkstra extends CanvasController {
	int times = DefineGraph.cref.time;
	SequentialTransition st = DefineGraph.cref.st;

	public Dijkstra(Node source) {
		source.minDistance = 0;
		// hang doi uu tien,các nut se duoc sap xep từ trọng so thap nhat đến cao nhat
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(source);
		while (!pq.isEmpty()) {
			Node u = pq.poll();
			System.out.println(u.name);
			// set nut vua bi goi ra khoi hang doi uu tien thanh mau chocolate
			FillTransition ft = new FillTransition(Duration.millis(times), u.circle);
			ft.setToValue(Color.CHOCOLATE);
			st.getChildren().add(ft);
			// set textFlow nut o dau trong hang doi uu tien
			String str = "";
			str = str.concat("Popped : Node(" + u.name + "), Current Distance: " + u.minDistance + "\n");
			final String str2 = str;
			FadeTransition fd = new FadeTransition(Duration.millis(10), textFlow);
			fd.setOnFinished(e -> {
				textFlow.appendText(str2);
			});
			fd.onFinishedProperty();
			st.getChildren().add(fd);

			System.out.println(u.name);

			// duyet cac cung ke voi nut duoc goi
			for (Edge e : u.adjacents) {
				if (e != null) {
					Node v = e.target;
					System.out.println("HERE " + v.name);
					if (u.minDistance + e.weight < v.minDistance) {// d[u]+w[e]<d[v]-> thi thuc hien them v vao hang doi
																	// uu tien
						pq.remove(v);
						v.minDistance = u.minDistance + e.weight;
						v.previous = u;
						pq.add(v);
						// set animation thay doi mau cua cung do sang mau do
						if (isUndirected()) {
							StrokeTransition ftEdge = new StrokeTransition(Duration.millis(times), e.line);
							ftEdge.setToValue(Color.FORESTGREEN);
							st.getChildren().add(ftEdge);
						} else if (isDirected()) {
							FillTransition ftEdge = new FillTransition(Duration.millis(times), e.line);
							ftEdge.setToValue(Color.FORESTGREEN);
							st.getChildren().add(ftEdge);
						}
						// set animation cho node duoc tham
						FillTransition ft1 = new FillTransition(Duration.millis(times), v.circle);
						ft1.setToValue(Color.FORESTGREEN);
						ft1.setOnFinished(ev -> {
							v.circle.distance.setText("Dist. : " + v.minDistance);
						});
						ft1.onFinishedProperty();
						st.getChildren().add(ft1);
						// set animation cho text in ben hiddenpane
						str = "\t";
						str = str.concat("Pushing : Node(" + v.name + "), (" + u.name + "--" + v.name + ") Distance : "
								+ v.minDistance + "\n");
						final String str1 = str;
						FadeTransition fd2 = new FadeTransition(Duration.millis(10), textFlow);
						fd2.setOnFinished(ev -> {
							textFlow.appendText(str1);
						});
						fd2.onFinishedProperty();
						st.getChildren().add(fd2);

					}
				}
			}
			// set màu cho tất cả các nút sau khi day ra khoi hang doi thanh màu tím xanh
			FillTransition ft2 = new FillTransition(Duration.millis(times), u.circle);
			ft2.setToValue(Color.BLUEVIOLET);
			st.getChildren().add(ft2);
		}

		// set animation cho các nut và cung sau khi thuc hien xong thuat toan
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
			// set cho nut playpause sang che do play
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
}