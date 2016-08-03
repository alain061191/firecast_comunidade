package br.gov.sc.cbm.e193comunitario.util;

import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import br.gov.sc.cbm.e193comunitario.Globals;
import br.gov.sc.cbm.e193comunitario.R;
import br.gov.sc.cbm.e193comunitario.jsonhelper.OcorrenciaJsonHelper;
import br.gov.sc.cbm.e193comunitario.model.Ocorrencia;
import br.gov.sc.cbm.e193comunitario.model.TipoEmergencia;
import br.gov.sc.cbm.e193comunitario.ui.DetalheOcorrenciaActivity;

public class Notificacao {
	public static final String TAG = "Notificacao";
	public static final double lat = 0;
	public static final double lon = 0;

	/**
	 * 
	 * @param context
	 *            Contexto da aplicação, is not null
	 * @param tituloNotification
	 *            Titulo a ser exibido na notificação ou null para não criar a
	 *            notificação
	 * @param descricaoNotification
	 *            Descrição a ser exibido na notificacao, use textos pequenos.
	 *            Não será usado caso titulo seja null
	 * @param idNotification
	 *            Define um identificador para poder manipular a notificação ou
	 *            null para criar um id diferente com base no tempo atual. 
	 *            Não será usado caso titulo seja null
	 * @param intentNotification
	 *            Intenção que será executada ou null para não efetuar ação
	 *            alguma. 
	 *            Não será usado caso titulo seja null
	 * @param vibrarNotification
	 *            If true then vibrate. 
	 *            Não será usado caso titulo seja null
	 * @param alertRingtone
	 *            If true then call Ringtone Alert
	 *            Não será usado caso titulo seja null
	 * @param msgToast
	 *            Mensagem a ser exibida em um Toast com LENGTH_SHORT, or null
	 *            para não exibir
	 */
	public void notificar(Context context, String tituloNotification,
			String descricaoNotification, Integer idNotification,
			Intent intentNotification, boolean vibrarNotification,
			String msgToast, boolean alertRingtone) {

		// se não tem titulo não tem notificacao
		if (tituloNotification != null && descricaoNotification != null && intentNotification != null) {
			
			// se não veio id algum, então cria um genérico
			if (idNotification == null) {
				Date agora = new Date();
				idNotification = (int) agora.getTime();
			}

			NotificationManager nm = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					context);
			// b.setTicker("Ticker");
			builder.setContentTitle(tituloNotification);
			builder.setContentText(descricaoNotification);
			builder.setSmallIcon(R.drawable.logo_e193comunitario);

			//Pega o parametro da intent
			String auxOcorrencia = intentNotification.getStringExtra(
					Globals.KEY_EXTRA_OCORRENCIA);			
			int idIcone = R.drawable.logo_e193comunitario;
			if (!Valida.isEmpty(auxOcorrencia)) {
				//veio alguma ocorrência
				Ocorrencia ocorrencia = OcorrenciaJsonHelper
						.ocorrenciaFromJson(auxOcorrencia);
				if (ocorrencia != null && ocorrencia.getTipoEmergencia() != null) {
					idIcone = getResourceIdByTipoEmergencia(ocorrencia.getTipoEmergencia());
					
					//para adicionar um botar de acção
					Intent intentMap = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + ocorrencia.getEnderecoWhichOutBairro()));
					PendingIntent pendingIntentMap = PendingIntent.getActivity(context, 0, intentMap, 1);
					builder.addAction(R.drawable.arrow_right, context.getString(R.string.notificacao_action_map), pendingIntentMap);
					
					//Adiciona o botão compartilhar 
					//Converte os IDs de cidade e ocorrencia para B36
					String idCidadeB36 = Integer.toString(ocorrencia.getCidade().getId(), 36);
					String idOcorrenciaB36 = Integer.toString(ocorrencia.getId(), 36);
					//concatena os IDs em uma URL para ser lida na Redes sociais
					String param = "?"+Globals.URL_PARAM_ID+"="+idCidadeB36+idOcorrenciaB36;
					String aux = ocorrencia.getDescriptionForHumanRead();
					aux = aux + "\nEnviado a partir de " + context.getString(R.string.app_name) + " - CBMSC\n" + Globals.URL_DETALHES_OCORRENCIA+param;
					Intent intentShare = new Intent();
					intentShare.setAction(Intent.ACTION_SEND);
					intentShare.putExtra(Intent.EXTRA_TEXT, aux);
					intentShare.setType("text/plain");
					PendingIntent pendingIntentShare = PendingIntent.getActivity(context, 0, intentShare, 1);
					builder.addAction(R.drawable.ic_share_12, context.getString(R.string.notificacao_action_share), pendingIntentShare);
				}
			}
			
			
			builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),idIcone));
			builder.setAutoCancel(true);
			builder.setCategory(TAG);
			
			if (vibrarNotification) {
				builder.setVibrate(new long[] { 150, 300, 150, 600 });
			}
			if (alertRingtone) {
				Uri som = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				builder.setSound(som);
			}
			
			
			
			//se for null não executa intent alguma
			if (intentNotification != null) {

				//SET_ACTION DEVE VIR POR PRIMEIRO!!!!
				intentNotification.setAction(idNotification.toString());
				PendingIntent p = PendingIntent.getActivity(context, 0,
						intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);
				builder.setContentIntent(p);
			}

			Notification notificationn = builder.build();
			// n.flags = Notification.FLAG_AUTO_CANCEL;
			// array com sequencia {parado, vibrando, parado, vibrando}


			nm.notify(TAG, idNotification, notificationn);
		}// fim notificação

		if (msgToast != null) {
			Toast.makeText(context, msgToast, Toast.LENGTH_SHORT).show();
		}
	}
	
	private int getResourceIdByTipoEmergencia(TipoEmergencia tipoEmergencia) {
		switch (tipoEmergencia.getId()) {
		case 8:
			//acidente de transito
			return R.drawable.act3;
		case 5:
			//atendimento pre-hospitalar
			return R.drawable.pre_ht;
		case 2: 
			//auxilios / apoios
			return R.drawable.aux;
		case 10: 
			//averiguação / corte de arvore
			return R.drawable.corte;
		case 11:
			//averiguação / mamejo inseto
			return R.drawable.colmeia;
		case 9: 
			//acoes perevetivas
			return R.drawable.prev;
		case 7: 
			//diversos
			return R.drawable.divers;
		case 1: 
			//incendio
			return R.drawable.fogo;
		case 6:
			//nao atendida
			return R.drawable.natendida;
		case 3:
			//produtos perigosos
			return R.drawable.perigoso;
		case 4: 
			//salvamento / busca / resgate
			return R.drawable.salva;
		default:
			return R.drawable.logo_e193comunitario;
		}
	}

	/**
	 * Cria a notificação baseado nas preferencias de notificação do usuario
	 * @param context
	 * @param ocorrencia
	 */
	public void notifyBasedOnPreferences(final Context context, final Ocorrencia ocorrencia) {

		// captura preferencias!
		boolean notificar = ManageSP
				.getBooleanFromSharedPreference(context, Globals.PREF_FILE,
						Globals.KEY_NOTIFICACAO_NOTIFICAR);
		boolean toast = ManageSP.getBooleanFromSharedPreference(
				context, Globals.PREF_FILE, Globals.KEY_NOTIFICACAO_TOAST);
		boolean vibrate = ManageSP
				.getBooleanFromSharedPreference(context, Globals.PREF_FILE,
						Globals.KEY_NOTIFICACAO_VIBRAR);
		boolean ringtone = ManageSP.getBooleanFromSharedPreference(
				context, Globals.PREF_FILE, Globals.KEY_NOTIFICACAO_SOM);

		if (notificar) {
			String msg = null;
			// prepara a msg;
			if (toast) {
				
				
				
				
				
				
				
				
				//TODO corrigir a latitude e longitude abixo
				if (ocorrencia.getDistancia(lat,lon) > 0) {
					msg = "FireCast Comunidade \nNova ocorrência a " + ocorrencia.getDistanciaFormatada(lat,lon);
				} else {
					msg = "FireCast Comunidade \nNova ocorrência (distância não determinada)";
				}
				Log.i(TAG, msg);
			}
			
			String tituloNotificacao = ocorrencia.getTipoEmergencia().getNome();
			String descricaoNotificacao = ocorrencia.getLogradouro();
			Integer idNotificacao = ocorrencia.getId();
			Intent intentNotificacao = new Intent(context,
					DetalheOcorrenciaActivity.class);
			intentNotificacao.putExtra(Globals.KEY_EXTRA_OCORRENCIA,
					OcorrenciaJsonHelper.ocorrenciaToJson(ocorrencia));
			this.notificar(context, tituloNotificacao, descricaoNotificacao,
					idNotificacao, intentNotificacao, vibrate, msg, ringtone);
		}
	}

}
