import {
  Component,
  Input,
  NgZone, OnDestroy,
  OnInit,
  ViewEncapsulation
} from '@angular/core';
import * as am4core from "@amcharts/amcharts4/core";
import * as am4charts from "@amcharts/amcharts4/charts";
import am4themes_animated from "@amcharts/amcharts4/themes/animated";
import {Statistics} from "../../model/statistics";

am4core.useTheme(am4themes_animated);

@Component({
  selector: 'graph',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.scss'],
})
export class GraphComponent implements OnInit, OnDestroy {
  private chart: am4charts.XYChart;

  private _data: Statistics;
  private dataset: any;

  constructor(private zone: NgZone) {
  }

  ngOnInit(): void {
  }

  @Input('data')
  set data(statistics: Statistics) {
    this._data = statistics;
    this.dataset = [];

    if (this._data.statusList) {
      this._data.statusList.forEach(status => {
        this.dataset.push({
          created: new Date(status.created),
          temperature: status.temperature,
          moisture: status.moisture,
          light: status.light
        });
      });

      if (this.chart) {
        this.chart.data = this.dataset;
      }
    }
  }

  ngAfterViewInit() {
    this.zone.runOutsideAngular(() => {
      let chart = am4core.create("chartdiv", am4charts.XYChart);

      chart.paddingRight = 20;

      chart.data = this.dataset;

      let dateAxis = chart.xAxes.push(new am4charts.DateAxis());
      dateAxis.renderer.grid.template.location = 0;

      let valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
      valueAxis.tooltip.disabled = false;
      valueAxis.renderer.minWidth = 35;

      let series = chart.series.push(new am4charts.LineSeries());
      series.dataFields.dateX = "created";
      series.dataFields.valueY = "temperature";
      series.tooltipText = "Temperature: {valueY.value} °C";
      series.legendSettings.valueText = "Temperature";

      series = chart.series.push(new am4charts.LineSeries());
      series.dataFields.dateX = "created";
      series.dataFields.valueY = "moisture";
      series.tooltipText = "Moisture: {valueY.value} %";
      series.legendSettings.valueText = "Moisture";

      series = chart.series.push(new am4charts.LineSeries());
      series.dataFields.dateX = "created";
      series.dataFields.valueY = "light";
      series.tooltipText = "Light: {valueY.value} %";
      series.legendSettings.valueText = "Light";

      chart.cursor = new am4charts.XYCursor();

      chart.legend = new am4charts.Legend();
      chart.legend.useDefaultMarker = true;
      let marker = chart.legend.markers.template.children.getIndex(0);
      marker.strokeWidth = 2;
      marker.strokeOpacity = 1;
      marker.stroke = am4core.color("#ccc");

      /*let scrollbarX = new am4charts.XYChartScrollbar();
      scrollbarX.series.push(series);
      chart.scrollbarX = scrollbarX;*/

      this.chart = chart;
    });
  }

  ngOnDestroy() {
    this.zone.runOutsideAngular(() => {
      if (this.chart) {
        this.chart.dispose();
      }
    });
  }

}
