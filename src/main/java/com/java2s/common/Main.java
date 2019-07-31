package com.java2s.common;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class Main {

	public static void main(String[] args) throws Exception {
		String userID = "212668";
		String mail = "supercyber@live.it";
		
		Integer month = Calendar.getInstance().get(Calendar.MONTH);
//		System.out.println(month);

		//		String sponsor = UtilityCommon.getSponsorInfo();
		//		System.out.println(sponsor);

		boolean value = false;
		//		UtilityCommonSixth.getInfoCardSixth();

		String aa = "€";
		System.out.println(aa);
		
		UtilityCommon.getRiepilogoGuadagno();




		//		List<CardBO> listaCarte2 = new ArrayList<CardBO>();
		//		
		//		CardBO card1 = new CardBO();
		//		card1.setNomeCarta("PIPPO");
		//		listaCarte2.add(card1);
		//		
		//		CardBO card2 = new CardBO();
		//		card2.setNomeCarta("PLUTO");
		//		listaCarte2.add(card2);
		//		
		//		UtilityCommon.addRecordJson(listaCarte2);


		if(!value) {

			//Login
			HttpResponse response = UtilityCommonSixth.loginSixth();
			String json = EntityUtils.toString(response.getEntity());

			String token = UtilityCommonSixth.readJsonLogin(json, "access_token", "data", null);

			//Login - 2
			UtilityCommonSixth.login2(token, mail);

			//    		String token2 = readJsonLogin(jsonDaily, "access_token");
			//    		https://prod.sixthcontinent.com/api/logins?access_token=OTQyM2RhMGMxZDAyYzQ3ZDIyMTFhOTBiY2RjZjEzNzFiMzg5YWJjMDEwNTQ0ZTM4NThlYTMwNmM2MGRmNzljMQ

			//Get All Cards
			String jsonAllCard = UtilityCommonSixth.getAllCardSixth();
			List<CardBO> listaCarte = UtilityCommonSixth.readJsonAllCard(jsonAllCard, "hits", "hits", null);
			System.out.println("Carte totali --> " + listaCarte.size());
			List<CardBO> listaCarteSbloccate = new ArrayList<CardBO>();
			List<CardBO> listaCarteErrore = new ArrayList<CardBO>();

			for (int i = 0; i < listaCarte.size(); i++) {
				CardBO cardBO = listaCarte.get(i);
				//				if(cardBO.getNomeCarta().contains("Morgan")) {
				cardBO = listaCarte.get(i+1);
				String jsonSpecificaCard = UtilityCommonSixth.getInfoCardSixth(token, userID, cardBO.getId(), cardBO.getSellerId());

				//Se andata in errore riprovo
				if(jsonSpecificaCard.contains("INVALID") || jsonSpecificaCard.contains("invalid")) {
					System.out.println("JSon invalid - riprovo la chiamata");
					UtilityCommonSixth.waitForSend(15);
					jsonSpecificaCard = UtilityCommonSixth.getInfoCardSixth(token, userID, cardBO.getId(), cardBO.getSellerId());
				}

				if(jsonSpecificaCard.contains("INVALID") || jsonSpecificaCard.contains("invalid")) {
					System.out.println("JSon invalid di nuovo");
				} else {
					UtilityCommonSixth.readJsonSpecificaCard(jsonSpecificaCard, "result", "promotion_type", "isFirstMonthMandatoryPremium", listaCarte, listaCarteSbloccate);
				}
				//				}
			}

			//Get info specifiche carte
			for (CardBO cardBO : listaCarte) {
				//				if(cardBO.getNomeCarta().equals("Zalando")) {
				//					break;
				//				}
				String jsonSpecificaCard = UtilityCommonSixth.getInfoCardSixth("MWE1MzRmZjQ4Y2RiZGNjMGQ1OTZkOGFlNmFkYTNjZmQyMWUxMDlhOThiODJlYjJjZTVhZGEwOTJlZjZlNzk0Mw", "212668", cardBO.getId(), cardBO.getSellerId());
				listaCarteSbloccate = UtilityCommonSixth.readJsonSpecificaCard(jsonSpecificaCard, "result", "promotion_type", "isFirstMonthMandatoryPremium", listaCarte, listaCarteSbloccate);
				UtilityCommonSixth.waitForSend(10);
			}

			UtilityCommon.addRecordJson(listaCarteSbloccate, "");

			//		for (CardBO cardBO : listaCarteSbloccate) {
			//			System.out.println("Nome --> " + cardBO.getNomeCarta());
			//		}

		}

	}

	public static long betweenDates(Date firstDate, Date secondDate) throws IOException
	{
		return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
	}



}
