package com.Toou.Toou.usecase;

import com.Toou.Toou.domain.model.StockDailyHistory;
import com.Toou.Toou.port.out.StockHistoryPort;
import com.Toou.Toou.port.out.StockOpenApiPort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListStockHistoryService implements ListStockHistoryUseCase {

	private final StockHistoryPort stockHistoryPort;
	private final StockOpenApiPort stockOpenApiPort;

	@Transactional
	@Override
	public Output execute(Input input) {
		//TODO: 더미데이터. 구현 후 삭제
		List<StockDailyHistory> dummyDailyHistories = parseDummyCsvData();
		return new Output(dummyDailyHistories);

//		List<StockDailyHistory> dailyHistories = stockHistoryPort.findAllHistoriesBetweenDates(
//				input.stockCode, input.dateFrom, input.dateTo);
//		if (!hasAllHistoriesBetweenDates(dailyHistories, input.dateFrom, input.dateTo)) {
//			// TODO: (1) stockOpenApiPort를 통해, 오픈 API에서 가져온 데이터를 StockDailyHistory로 변환하고 위 dailyHistories와 합쳐준 뒤 응답해준다.
//			// TODO: (2) 오픈 API에서 가져온 데이터를 StockDailyHistory로 변환한 결과에 대해 stockHistoryPort를 통해서 DB에 저장해준다.
//		}
//		return new Output(dailyHistories);
	}

	private boolean hasAllHistoriesBetweenDates(List<StockDailyHistory> histories, LocalDate dateFrom,
			LocalDate dateTo) {
		// TODO: histories에 있는 각 history들의 date를 확인해서 dateFrom~dateTo 사이 모든 날짜를 커버하는지 확인하는 로직 필요
		return false;
	}

	private List<StockDailyHistory> parseDummyCsvData() {
		List<StockDailyHistory> stockDailyHistories = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new StringReader(dummyCsv()));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String line;
		try {
			// Skip header
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				LocalDate date = LocalDate.parse(tokens[0], formatter);
				List<Long> prices = Arrays.asList(
						Long.parseLong(tokens[1]),
						Long.parseLong(tokens[2]),
						Long.parseLong(tokens[3]),
						Long.parseLong(tokens[4])
				);
				stockDailyHistories.add(
						new StockDailyHistory(1L, "A079980", "휴비스", prices, date));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stockDailyHistories;
	}

	private String dummyCsv() {
		return """
				날짜,시가,고가,저가,종가,거래량,등락률
				2023-01-02,4960,4960,4705,4800,20898,-1.7400204708290685
				2023-01-03,4755,4770,4695,4740,17994,-1.25
				2023-01-04,4780,4825,4700,4820,25045,1.6877637130801686
				2023-01-05,4820,4845,4780,4800,18268,-0.4149377593360996
				2023-01-06,4800,4850,4785,4800,23250,0.0
				2023-01-09,4800,4950,4800,4915,38676,2.3958333333333335
				2023-01-10,4925,4985,4915,4950,12268,0.7121057985757884
				2023-01-11,4950,5020,4940,5000,19832,1.0101010101010102
				2023-01-12,4960,5030,4935,4945,14224,-1.0999999999999999
				2023-01-13,4980,4995,4940,4960,12751,0.3033367037411527
				2023-01-16,4980,5020,4955,4960,16051,0.0
				2023-01-17,4940,5010,4940,4970,14922,0.20161290322580644
				2023-01-18,4995,5010,4935,4960,12019,-0.2012072434607646
				2023-01-19,4925,5000,4925,4990,13256,0.6048387096774194
				2023-01-20,4965,5030,4965,5030,14504,0.8016032064128256
				2023-01-25,5080,5080,4990,5030,20563,0.0
				2023-01-26,5020,5060,4990,5010,32526,-0.3976143141153081
				2023-01-27,4980,5050,4980,5010,32360,0.0
				2023-01-30,5040,5050,4980,5020,19831,0.19960079840319359
				2023-01-31,5010,5050,4995,5010,17519,-0.199203187250996
				2023-02-01,5030,5070,4990,5020,15092,0.19960079840319359
				2023-02-02,5050,5080,4970,5020,29878,0.0
				2023-02-03,5020,5040,4955,5000,27726,-0.398406374501992
				2023-02-06,5010,5090,5010,5090,49853,1.7999999999999998
				2023-02-07,5080,5090,5000,5080,27616,-0.19646365422396855
				2023-02-08,5070,5100,5010,5070,26717,-0.19685039370078738
				2023-02-09,5070,5080,5020,5050,12876,-0.39447731755424065
				2023-02-10,5050,5050,5000,5020,22166,-0.594059405940594
				2023-02-13,4960,5020,4925,4975,17794,-0.8964143426294822
				2023-02-14,4975,5030,4960,4995,13170,0.4020100502512563
				2023-02-15,5000,5020,4805,4875,49962,-2.4024024024024024
				2023-02-16,4910,4940,4875,4890,31310,0.3076923076923077
				2023-02-17,4800,4885,4785,4815,40152,-1.5337423312883436
				2023-02-20,4875,4890,4815,4850,17808,0.726895119418484
				2023-02-21,4850,5020,4840,4940,40802,1.8556701030927836
				2023-02-22,4905,4940,4845,4860,37128,-1.6194331983805668
				2023-02-23,4905,4905,4850,4865,13701,0.102880658436214
				2023-02-24,4920,4920,4835,4845,20667,-0.41109969167523125
				2023-02-27,4775,4815,4755,4800,24358,-0.9287925696594427
				2023-02-28,4755,4810,4755,4810,18199,0.20833333333333334
				2023-03-02,4800,4815,4720,4780,50393,-0.6237006237006237
				2023-03-03,4780,4785,4725,4735,24482,-0.9414225941422595
				2023-03-06,4755,4800,4720,4760,25523,0.5279831045406547
				2023-03-07,4810,4935,4765,4935,53586,3.6764705882352944
				2023-03-08,4925,4925,4835,4845,28408,-1.82370820668693
				2023-03-09,4870,4915,4820,4905,28312,1.238390092879257
				2023-03-10,4870,5000,4760,4820,31629,-1.7329255861365953
				2023-03-13,4780,4780,4625,4670,39574,-3.112033195020747
				2023-03-14,4670,4700,4470,4470,69662,-4.282655246252676
				2023-03-15,4540,4550,4500,4530,20048,1.342281879194631
				2023-03-16,4500,4500,4400,4430,30902,-2.207505518763797
				2023-03-17,4430,4545,4430,4535,51008,2.3702031602708806
				2023-03-20,4550,4595,4525,4535,16014,0.0
				2023-03-21,4560,4560,4510,4525,11522,-0.2205071664829107
				2023-03-22,4535,4545,4500,4515,33661,-0.22099447513812157
				2023-03-23,4455,5210,4455,4900,610850,8.527131782945736
				2023-03-24,4865,4900,4750,4760,79729,-2.857142857142857
				2023-03-27,4740,4760,4720,4755,24351,-0.10504201680672269
				2023-03-28,4725,4890,4725,4855,23868,2.1030494216614093
				2023-03-29,4835,5000,4825,4985,51289,2.677651905252317
				2023-03-30,5000,5100,4965,4980,72014,-0.10030090270812438
				2023-03-31,4980,5020,4940,4980,36497,0.0
				2023-04-03,5050,5050,4750,4985,36613,0.1004016064257028
				2023-04-04,5020,5130,4990,5100,84531,2.3069207622868606
				2023-04-05,5100,5300,5100,5290,91162,3.7254901960784315
				2023-04-06,5290,5320,5180,5300,61458,0.1890359168241966
				2023-04-07,5300,5310,5210,5290,38895,-0.18867924528301888
				2023-04-10,5290,5320,5100,5160,39930,-2.4574669187145557
				2023-04-11,5160,5250,5100,5170,28464,0.1937984496124031
				2023-04-12,5220,5370,5170,5330,92258,3.0947775628626695
				2023-04-13,5370,5370,5240,5290,29877,-0.7504690431519699
				2023-04-14,5350,5350,5250,5280,32478,-0.1890359168241966
				2023-04-17,5280,5320,5150,5210,33276,-1.3257575757575757
				2023-04-18,5180,5210,5080,5120,63743,-1.727447216890595
				2023-04-19,5120,5230,5090,5170,36954,0.9765625
				2023-04-20,5200,5200,5060,5140,33427,-0.5802707930367506
				2023-04-21,5140,5160,4980,5100,33546,-0.7782101167315175
				2023-04-24,5080,5080,4795,4850,66484,-4.901960784313726
				2023-04-25,4800,4850,4700,4760,60005,-1.8556701030927836
				2023-04-26,4745,4750,4630,4665,50442,-1.9957983193277309
				2023-04-27,4610,4665,4525,4575,38167,-1.929260450160772
				2023-04-28,4660,4720,4525,4545,35134,-0.6557377049180327
				2023-05-02,4570,4605,4545,4570,38003,0.5500550055005501
				2023-05-03,4530,4640,4530,4580,28386,0.2188183807439825
				2023-05-04,4580,4645,4560,4645,16440,1.4192139737991267
				2023-05-08,4700,4750,4645,4710,22199,1.3993541442411195
				2023-05-09,4700,4745,4645,4735,19659,0.5307855626326964
				2023-05-10,4680,4820,4680,4715,27225,-0.42238648363252373
				2023-05-11,4760,4820,4740,4790,29896,1.5906680805938493
				2023-05-12,4750,4815,4665,4670,12595,-2.5052192066805845
				2023-05-15,4670,4735,4560,4650,34077,-0.4282655246252677
				2023-05-16,4620,4680,4590,4590,28400,-1.2903225806451613
				2023-05-17,4655,4700,4580,4620,26192,0.6535947712418301
				2023-05-18,4690,4695,4525,4660,80259,0.8658008658008658
				2023-05-19,4695,4820,4610,4785,108730,2.682403433476395
				2023-05-22,4785,4835,4740,4780,26027,-0.10449320794148381
				2023-05-23,4780,4805,4740,4770,25435,-0.20920502092050208
				2023-05-24,4770,4770,4695,4770,21634,0.0
				2023-05-25,4730,4765,4680,4750,30459,-0.41928721174004197
				2023-05-26,4750,4750,4640,4695,28337,-1.1578947368421053
				2023-05-30,4675,4775,4630,4700,37521,0.10649627263045794
				2023-05-31,4650,4710,4625,4640,77940,-1.276595744680851
				2023-06-01,4680,4680,4595,4595,22813,-0.9698275862068966
				2023-06-02,4605,4695,4585,4660,39749,1.4145810663764962
				2023-06-05,4660,4705,4650,4680,19293,0.4291845493562232
				2023-06-07,4660,4745,4660,4720,40733,0.8547008547008548
				2023-06-08,4700,4725,4685,4720,14188,0.0
				2023-06-09,4730,4730,4665,4720,15988,0.0
				2023-06-12,4725,4725,4670,4700,32111,-0.423728813559322
				2023-06-13,4705,4745,4660,4710,35258,0.2127659574468085
				2023-06-14,4735,4735,4670,4700,52278,-0.21231422505307856
				2023-06-15,4705,4705,4580,4590,58370,-2.3404255319148937
				2023-06-16,4610,4695,4585,4680,35690,1.9607843137254901
				2023-06-19,4680,4685,4630,4640,27586,-0.8547008547008548
				2023-06-20,4640,4790,4630,4725,52406,1.8318965517241377
				2023-06-21,4770,4770,4640,4645,43340,-1.6931216931216932
				2023-06-22,4650,4755,4615,4700,72560,1.1840688912809472
				2023-06-23,4705,4735,4675,4685,27821,-0.3191489361702127
				2023-06-26,4680,4745,4640,4670,32491,-0.32017075773745995
				2023-06-27,4645,4690,4645,4670,5160,0.0
				2023-06-28,4675,4720,4675,4700,25360,0.6423982869379015
				2023-06-29,4720,4795,4660,4675,37834,-0.5319148936170213
				2023-06-30,4680,4710,4645,4710,18546,0.7486631016042781
				2023-07-03,4710,4760,4640,4670,30478,-0.8492569002123143
				2023-07-04,4670,4735,4625,4645,33745,-0.5353319057815845
				2023-07-05,4645,4655,4480,4490,77279,-3.3369214208826694
				2023-07-06,4555,4555,4365,4400,45065,-2.0044543429844097
				2023-07-07,4400,4400,4295,4395,36318,-0.11363636363636363
				2023-07-10,4305,4355,4295,4295,45902,-2.2753128555176336
				2023-07-11,4295,4380,4295,4370,29682,1.7462165308498252
				2023-07-12,4340,4425,4325,4415,7470,1.0297482837528604
				2023-07-13,4435,4450,4390,4425,17275,0.22650056625141565
				2023-07-14,4425,4445,4365,4410,25832,-0.3389830508474576
				2023-07-17,4410,4430,4285,4305,29870,-2.380952380952381
				2023-07-18,4295,4320,4245,4250,26382,-1.2775842044134729
				2023-07-19,4305,4335,4200,4220,34585,-0.7058823529411765
				2023-07-20,4220,4220,4150,4190,33354,-0.7109004739336493
				2023-07-21,4220,4265,4130,4190,30227,0.0
				2023-07-24,4190,4190,4000,4000,99995,-4.534606205250596
				2023-07-25,4095,4095,3845,3860,141029,-3.5000000000000004
				2023-07-26,3795,3835,3660,3690,91641,-4.404145077720207
				2023-07-27,3625,3970,3625,3925,41714,6.368563685636857
				2023-07-28,4000,4050,3900,4000,36426,1.910828025477707
				2023-07-31,4000,4075,3975,4010,50991,0.25
				2023-08-01,4010,4135,4000,4050,62007,0.997506234413965
				2023-08-02,4125,4175,4060,4060,54062,0.24691358024691357
				2023-08-03,4085,4995,4060,4580,603561,12.807881773399016
				2023-08-04,4830,4835,4450,4550,180687,-0.6550218340611353
				2023-08-07,4550,4550,4350,4410,78464,-3.076923076923077
				2023-08-08,4410,4470,4200,4305,52312,-2.380952380952381
				2023-08-09,4305,4400,4260,4400,53749,2.2067363530778166
				2023-08-10,4360,4485,4300,4400,54408,0.0
				2023-08-11,4350,4465,4350,4435,52170,0.7954545454545454
				2023-08-14,4430,4440,4350,4395,52615,-0.9019165727170236
				2023-08-16,4450,4465,4260,4425,85452,0.6825938566552902
				2023-08-17,4425,5100,4320,4985,1364650,12.655367231638417
				2023-08-18,5400,6480,5400,6480,5328487,29.989969909729187
				2023-08-21,8420,8420,8420,8420,1358868,29.938271604938272
				2023-08-22,8600,10600,7980,8420,41843359,0.0
				2023-08-23,8820,10940,8780,10000,43036262,18.76484560570071
				2023-08-24,9400,10580,9000,9330,13036001,-6.7
				2023-08-25,9520,9750,7980,8230,9398621,-11.789924973204716
				2023-08-28,8100,8330,7730,8250,4969415,0.24301336573511542
				2023-08-29,8040,8460,7700,7960,6066645,-3.5151515151515147
				2023-08-30,7860,8100,7760,8000,1923651,0.5025125628140703
				2023-08-31,8130,8130,7120,7270,3530621,-9.125
				2023-09-01,7270,7370,7070,7260,1182862,-0.1375515818431912
				2023-09-04,7220,7290,6930,7160,956683,-1.3774104683195594
				2023-09-05,7200,7440,7100,7310,1333816,2.094972067039106
				2023-09-06,7280,7290,6900,6900,947889,-5.60875512995896
				2023-09-07,6920,7290,6610,6690,1957286,-3.0434782608695654
				2023-09-08,6690,6810,6600,6710,653905,0.29895366218236175
				2023-09-11,6710,6790,6570,6700,497663,-0.14903129657228018
				2023-09-12,6660,6880,6350,6400,875058,-4.477611940298507
				2023-09-13,6310,6520,6210,6210,553280,-2.96875
				2023-09-14,6210,6590,6200,6360,731729,2.4154589371980677
				2023-09-15,6400,6560,6270,6400,499775,0.628930817610063
				2023-09-18,6300,6430,6200,6240,305851,-2.5
				2023-09-19,6270,6400,6110,6260,349837,0.3205128205128205
				2023-09-20,6220,6630,5980,6140,1032149,-1.9169329073482428
				2023-09-21,6200,6420,5820,5880,931762,-4.234527687296417
				2023-09-22,5790,6100,5680,5690,346387,-3.231292517006803
				2023-09-25,5640,5790,5560,5650,222523,-0.7029876977152899
				2023-09-26,5650,5700,5500,5560,204253,-1.592920353982301
				2023-09-27,5470,5680,5420,5480,280535,-1.4388489208633095
				2023-10-04,5410,5440,5170,5200,488947,-5.109489051094891
				2023-10-05,5270,5350,5110,5140,305788,-1.153846153846154
				2023-10-06,5140,5340,5140,5300,371761,3.11284046692607
				2023-10-10,5310,5460,5060,5080,613014,-4.150943396226415
				2023-10-11,5140,5420,5140,5210,354174,2.559055118110236
				2023-10-12,5220,5470,5200,5390,385152,3.45489443378119
				2023-10-13,5400,5400,4990,5020,422635,-6.8645640074211505
				2023-10-16,4990,5020,4795,4890,321185,-2.589641434262948
				2023-10-17,4935,5120,4925,5060,148480,3.476482617586912
				2023-10-18,5020,5400,4950,5040,263958,-0.3952569169960474
				2023-10-19,4935,5050,4840,4865,216519,-3.4722222222222223
				2023-10-20,4820,4825,4615,4680,253511,-3.802672147995889
				2023-10-23,4570,4775,4535,4605,166867,-1.6025641025641024
				2023-10-24,4590,4715,4410,4700,179640,2.0629750271444083
				2023-10-25,4730,4835,4680,4690,133665,-0.2127659574468085
				2023-10-26,4565,4605,4360,4415,212984,-5.863539445628998
				2023-10-27,4400,4470,4325,4350,131846,-1.4722536806342015
				2023-10-30,4300,4430,4285,4360,156373,0.22988505747126436
				2023-10-31,4360,4415,4175,4230,227584,-2.981651376146789
				2023-11-01,4230,4375,4230,4260,114384,0.7092198581560284
				2023-11-02,4295,4435,4290,4435,147997,4.107981220657277
				2023-11-03,4500,4505,4330,4505,135475,1.5783540022547913
				2023-11-06,4570,4660,4525,4580,191171,1.6648168701442843
				2023-11-07,4600,4600,4420,4450,161945,-2.8384279475982535
				2023-11-08,4465,4535,4285,4345,165479,-2.359550561797753
				2023-11-09,4305,4935,4285,4345,3065797,0.0
				2023-11-10,4305,4330,4095,4160,409245,-4.25776754890679
				2023-11-13,4210,4295,4100,4110,196460,-1.201923076923077
				2023-11-14,4120,4220,4100,4200,148077,2.18978102189781
				2023-11-15,4265,4345,4225,4305,170286,2.5
				2023-11-16,4325,4400,4210,4380,149539,1.7421602787456445
				2023-11-17,4345,4350,4205,4250,151854,-2.968036529680365
				2023-11-20,4255,4330,4210,4325,96394,1.7647058823529411
				2023-11-21,4325,4500,4265,4425,174795,2.312138728323699
				2023-11-22,4355,4480,4335,4435,124766,0.22598870056497175
				2023-11-23,4435,4495,4380,4390,115849,-1.0146561443066515
				2023-11-24,4390,4430,4330,4415,79723,0.5694760820045558
				2023-11-27,4440,4455,4325,4365,90531,-1.1325028312570782
				2023-11-28,4350,4480,4260,4280,157402,-1.9473081328751431
				2023-11-29,4280,4335,4200,4205,106514,-1.7523364485981308
				2023-11-30,4220,4530,4150,4510,363169,7.253269916765754
				2023-12-01,4405,4435,4245,4280,207664,-5.099778270509978
				2023-12-04,4300,4380,4205,4310,117841,0.7009345794392523
				2023-12-05,4275,4340,4200,4240,124904,-1.6241299303944314
				2023-12-06,4205,4275,4155,4230,158596,-0.2358490566037736
				2023-12-07,4230,4240,4140,4155,119044,-1.773049645390071
				2023-12-08,4170,4190,4130,4180,91809,0.601684717208183
				2023-12-11,4175,4215,4145,4215,67041,0.8373205741626795
				2023-12-12,4215,4230,4130,4170,94818,-1.0676156583629894
				2023-12-13,4150,4155,4080,4115,127320,-1.3189448441247003
				2023-12-14,4115,4160,4060,4070,119611,-1.0935601458080195
				2023-12-15,4065,4245,4065,4245,118705,4.2997542997543
				2023-12-18,4270,4270,4175,4235,71075,-0.23557126030624262
				2023-12-19,4250,4250,4160,4200,84221,-0.8264462809917356
				2023-12-20,4200,4415,4190,4305,269219,2.5
				2023-12-21,4295,4325,4120,4175,193532,-3.0197444831591174
				2023-12-22,4230,4230,4125,4155,91929,-0.47904191616766467
				2023-12-26,4155,4210,4080,4090,128908,-1.5643802647412757
				2023-12-27,4065,4200,4050,4120,98290,0.7334963325183375
				2023-12-28,4080,4145,4060,4120,62332,0.0
				""";
	}
}
