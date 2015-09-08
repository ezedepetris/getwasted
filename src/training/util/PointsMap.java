package training.util;

import training.map.MapActivity;
import android.graphics.PointF;

public class PointsMap {

	public PointF getPoint(int place, int selector) {
		// place hace referencia a la posicion en el arreglo. Coincide con el
		// que
		// esta en el archivo XML para sacar los px py.
		float px = 0;
		float py = 0;
		switch (selector) {
		case MapActivity.PAVILION_1:
			switch (place) {
			case 0:
				px = 1300;
				py = 1507;
				break;
			case 1:
				px = 1334;
				py = 1512;
				break;
			case 2:
				px = 1318;
				py = 1415;
				break;
			case 3:
				px = 1320;
				py = 1395;
				break;
			case 4:
				px = 1323;
				py = 1376;
				break;
			case 5:
				px = 1349;
				py = 1350;
				break;
			case 6:
				px = 1345;
				py = 1373;
				break;
			case 7:
				px = 1343;
				py = 1394;
				break;
			case 8:
				px = 1341;
				py = 1414;
				break;
			case 9:
				px = 1336;
				py = 1452;
				break;
			case 10:
				px = 1340;
				py = 1432;
				break;
			case 11:
				px = 1314;
				py = 1443;
				break;
			case 12:
				px = 1318;
				py = 1417;
				break;
			case 13:
				px = 1322;
				py = 1396;
				break;
			case 14:
				px = 1324;
				py = 1375;
				break;
			case 15:
				px = 1326;
				py = 1351;
				break;
			case 16:
				px = 1350;
				py = 1351;
				break;
			case 17:
				px = 1347;
				py = 1378;
				break;
			case 18:
				px = 1342;
				py = 1394;
				break;
			case 19:
				px = 1341;
				py = 1416;
				break;
			case 20:
				px = 1336;
				py = 1452;
				break;
			case 21:
				px = 1337;
				py = 1433;
				break;
			case 22:
				px = 1283;
				py = 1303;
				break;
			case 23:
				px = 1302;
				py = 1258;
				break;
			case 24:
				px = 1338;
				py = 1272;
				break;
			case 25:
				px = 1374;
				py = 1285;
				break;
			case 26:
				px = 1285;
				py = 1303;
				break;
			case 27:
				px = 1300;
				py = 1258;
				break;
			case 28:
				px = 1339;
				py = 1271;
				break;
			case 29:
				px = 1375;
				py = 1286;
				break;
			case 30:
				px = 1333;
				py = 1227;
				break;
			case 31:
				px = 1337;
				py = 1201;
				break;
			case 32:
				px = 1338;
				py = 1171;
				break;
			case 33:
				px = 1361;
				py = 1173;
				break;
			case 34:
				px = 1357;
				py = 1202;
				break;
			case 35:
				px = 1356;
				py = 1227;
				break;
			case 36:
				px = 1341;
				py = 1228;
				break;
			case 37:
				px = 1346;
				py = 1198;
				break;
			case 38:
				px = 1349;
				py = 1175;
				break;
			default:
				break;
			}
			break;

		case MapActivity.PAVILION_2:
			switch (place) {
			case 0:
				px = 309;
				py = 981;
				break;
			case 1:
				px = 233;
				py = 972;
				break;
			case 2:
				px = 264;
				py = 938;
				break;
			case 3:
				px = 288;
				py = 933;
				break;
			case 4:
				px = 298;
				py = 854;
				break;
			case 5:
				px = 295;
				py = 880;
				break;
			case 6:
				px = 292;
				py = 898;
				break;
			case 7:
				px = 290;
				py = 925;
				break;
			case 8:
				px = 285;
				py = 945;
				break;
			case 9:
				px = 266;
				py = 944;
				break;
			case 10:
				px = 268;
				py = 918;
				break;
			case 11:
				px = 272;
				py = 893;
				break;
			case 12:
				px = 273;
				py = 875;
				break;
			case 13:
				px = 276;
				py = 849;
				break;
			case 14:
				px = 274;
				py = 976;
				break;
			case 15:
				px = 352;
				py = 858;
				break;
			case 16:
				px = 336;
				py = 856;
				break;
			case 17:
				px = 318;
				py = 854;
				break;
			case 18:
				px = 272;
				py = 844;
				break;
			case 19:
				px = 277;
				py = 862;
				break;
			case 20:
				px = 276;
				py = 879;
				break;
			case 21:
				px = 272;
				py = 897;
				break;
			case 22:
				px = 291;
				py = 899;
				break;
			case 23:
				px = 296;
				py = 881;
				break;
			case 24:
				px = 269;
				py = 916;
				break;
			case 25:
				px = 300;
				py = 847;
				break;
			case 26:
				px = 298;
				py = 863;
				break;
			}
			break;
		case MapActivity.PAVILION_3:
			switch (place) {
			case 0:
				px = 428;
				py = 933;
				break;
			case 1:
				px = 445;
				py = 919;
				break;
			case 2:
				px = 448;
				py = 905;
				break;
			case 3:
				px = 447;
				py = 892;
				break;
			case 4:
				px = 449;
				py = 877;
				break;
			case 5:
				px = 449;
				py = 861;
				break;
			case 6:
				px = 430;
				py = 860;
				break;
			case 7:
				px = 429;
				py = 875;
				break;
			case 8:
				px = 429;
				py = 890;
				break;
			case 9:
				px = 372;
				py = 989;
				break;
			case 10:
				px = 394;
				py = 987;
				break;
			case 11:
				px = 417;
				py = 990;
				break;
			case 12:
				px = 439;
				py = 988;
				break;
			case 13:
				px = 467;
				py = 990;
				break;
			case 14:
				px = 443;
				py = 948;
				break;
			case 15:
				px = 444;
				py = 928;
				break;
			case 16:
				px = 446;
				py = 911;
				break;
			case 17:
				px = 448;
				py = 894;
				break;
			case 18:
				px = 426;
				py = 904;
				break;
			case 19:
				px = 425;
				py = 927;
				break;
			case 20:
				px = 423;
				py = 948;
				break;
			case 21:
				px = 438;
				py = 864;
				break;
			}
			break;
		case MapActivity.MORE_PLACES:
			switch (place) {
			case 0:
				px = 966;
				py = 1282;
				break;
			case 1:
				px = 252;
				py = 671;
				break;
			case 2:
				px = 1314;
				py = 1433;
				break;
			case 3:
				px = 1191;
				py = 1470;
				break;
			case 4:
				px = 1328;
				py = 1349;
				break;
			case 5:
				px = 290;
				py = 917;
				break;
			case 6:
				px = 418;
				py = 988;
				break;
			case 7:
				px = 439;
				py = 986;
				break;
			case 8:
				px = 373;
				py = 988;
				break;
			case 9:
				px = 439;
				py = 988;
				break;
			case 10:
				px = 504;
				py = 229;
				break;
			case 11:
				px = 346;
				py = 855;
				break;
			case 12:
				px = 288;
				py = 946;
				break;
			case 13:
				px = 1455;
				py = 1518;
				break;
			case 14:
				px = 75;
				py = 261;
				break;
			case 15:
				px = 1280;
				py = 1330;
				break;
			case 16:
				px = 319;
				py = 861;
				break;
			case 17://agro y vet
				px = 856;
				py = 1216;
				break;
			case 18://economica
				px = 553;
				py = 730;
				break;
			case 19://exactas
				px = 463;
				py = 696;
				break;
			case 20://ingieniera
				px = 549;
				py = 729;
				break;
			case 21://humnas
				px = 327;
				py = 705;
				break;
			default:
				break;
			}

			break;
		}

		return new PointF(px, py);
	}

}
