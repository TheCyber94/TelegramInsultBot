package com.java2s.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class MyBots extends TelegramLongPollingBot{

	private boolean mock = false;

//	private void waitForSend(int seconds) throws InterruptedException {
//
//		if(seconds != 0) {
//			Thread.sleep(seconds*1000);
//		}
//
//	}

	public void onUpdateReceived(Update update)  {

		try {
			System.out.println("Inizio...");
			
			//Controlliamo che il messaggio contenga un testo
			if(update.hasMessage() && update.getMessage().hasText()) {
				
				String userName = "";
				if(update.getMessage().getFrom()!=null && update.getMessage().getFrom().getUserName()!=null) {
					userName = update.getMessage().getFrom().getUserName();
				}
				
				//update.getMessage().getChat().getUserName();
				//				Long chatId = new Long(0);
				//update.getMessage().getChatId();

				String received_text = update.getMessage().getText(); // Assegniamo ad una varibile il testo ricevuto
				String[] received_text_list = received_text.split("@"); //Splitto il testo dalla chiocciola (serve se do comando da gruppo)
				received_text = received_text_list[0]; //Prendo fino a prima della chiocciola

				if(received_text.contains("insulta")){
					String[] currencies = received_text.split(" ");
					insulta(currencies, update.getMessage().getChatId(), update.getMessage().getFrom().getId().toString(), update.getMessage().getMessageId(), userName, false);
				}
			} else if(update.hasCallbackQuery() && update.getCallbackQuery().getData()!=null) { //Controlliamo se il messaggio ha una callBack
//				String userName = "";
//				if(update.getCallbackQuery().getFrom().getUserName()!=null) {
//					userName = update.getCallbackQuery().getFrom().getUserName();
//				}
//
//				System.out.println("CallBack - " + update.getCallbackQuery().getData());
//				String msgCallBack = update.getCallbackQuery().getData();
//
//				if(msgCallBack.equals("/nosponsor")){
//					noSponsor(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getFrom().getId().toString(), update.getCallbackQuery().getMessage().getMessageId(), userName, true);
//				} else if(msgCallBack.equals("/turbo")){
//					turbo(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getFrom().getId().toString(), update.getCallbackQuery().getMessage().getMessageId(), userName, true);
//				} else if(msgCallBack.equals("/familybox")){
//					familyBox(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getFrom().getId().toString(), update.getCallbackQuery().getMessage().getMessageId(), userName, true);
//				} else if(msgCallBack.equals("/start")){
//					start(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getFrom().getId().toString(), update.getCallbackQuery().getMessage().getMessageId(), userName, true);
//				} else if(msgCallBack.equals("/sponsor")){
//					sponsor(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getFrom().getId().toString(), update.getCallbackQuery().getMessage().getMessageId(), userName, true);
//				} else if(msgCallBack.equals("/nosponsorpremium")){
//					nosponsorpremium(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getFrom().getId().toString(), update.getCallbackQuery().getMessage().getMessageId(), userName, true);
//				} else if(msgCallBack.equals("/riepilogoguadagno")){
//					riepilogoguadagno(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getFrom().getId().toString(), update.getCallbackQuery().getMessage().getMessageId(), userName, true);
//				} else if(msgCallBack.equals("/riepilogoextraprofit")){
//					riepilogoextraprofit(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getFrom().getId().toString(), update.getCallbackQuery().getMessage().getMessageId(), userName, true);
//				}
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insulta(String[] currencies, Long senderId, String chatId, Integer messageId, String userName, boolean isCallBack) throws Exception, TelegramApiException {
		System.out.println("*** COMANDO /insulta INIZIO - Utente |".concat(userName).trim().concat("| ***"));
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i < currencies.length; i++){
			sb.append(currencies[i] + " ");
		}
		sb.append(" è una merdaccia!");
		String text = sb.toString();
		SendMessage message = new SendMessage(); // Inizializziamo un'altra variabile per l'invio del messaggio
		Long sender_id = senderId; // Assegniamo ad una variabile l'Id della chat
		//					message.setChatId(sender_id); // Settiamo l'Id della chat
		if(isCallBack) {
			message.setChatId(sender_id); //Risponde in chat privata
		} else {
			message.setChatId(chatId); //Risponde in chat privata
		}

		sendMsg(text, message, false);



		//Elimino il messaggio inviato per tenere pulita la chat
//		try {
//			DeleteMessage deleteMessage = new DeleteMessage(sender_id, messageId);
//			execute(deleteMessage);
//		} catch (Exception e) {
//			System.out.println("*** COMANDO /insulta ERRORE ELIMINAZIONE MESSAGGIO - Utente |".concat(userName).trim().concat("| ***"));
//			//						e.printStackTrace();
//		}

		//					UtilityCommon.logFileApp("***COMANDO /nosponsor FINE - Utente |".concat(userName).trim().concat("| ***"), "INFO");
		System.out.println("***COMANDO /insulta FINE - Utente |".concat(userName).trim().concat("| ***"));
	}


	private void sendMsg(String msg, SendMessage message, boolean keyboard) throws TelegramApiException {
		message.setText(msg); // Settiamo il messaggio
		message.enableMarkdown(true);

		execute(message);
	}

	public String getBotUsername() {

		return "SxcGuidePlus_bot"; // Nome del bot

	}

	public String getBotToken() {

		return "875768440:AAENMHATFkBA3Jj5yqm77pC-LwcZObqefYM"; //@SKYMerdBot_bot

	}


}