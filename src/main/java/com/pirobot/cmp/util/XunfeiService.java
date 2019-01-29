package com.pirobot.cmp.util;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import com.iflytek.cloud.speech.TextUnderstander;
import com.iflytek.cloud.speech.TextUnderstanderListener;
import com.iflytek.cloud.speech.UnderstanderResult;

public class XunfeiService {
	protected final Log logger = LogFactory.getLog(XunfeiService.class);
	private BlockingQueue<String> understandTextResult = new ArrayBlockingQueue<String>(1);
	private TextUnderstander mTextUnderstander;
	static{
		SpeechUtility.createUtility("appid="+ConfigManager.getInstance().getStringValue("XUNFEI_APPID"));
	}
	public XunfeiService(){
		mTextUnderstander = TextUnderstander.createTextUnderstander();
		mTextUnderstander.setParameter(SpeechConstant.DOMAIN, "iat");
		mTextUnderstander.setParameter(SpeechConstant.NLP_VERSION, "2.0");
		mTextUnderstander.setParameter(SpeechConstant.RESULT_TYPE, "json");
	}
	
	public String understandText(String sentence)
	{
		String result = "";
		if (mTextUnderstander.isUnderstanding())
			mTextUnderstander.cancel();
		else {
			mTextUnderstander.understandText(sentence, textListener);
			try{
				result = understandTextResult.take();					
			}catch(Exception e){}
		}
		return result;
	}
	
	private TextUnderstanderListener textListener = new TextUnderstanderListener() {

		@Override
		public void onResult(final UnderstanderResult result) {
			if(result != null)
			{
				understandTextResult.offer(result.getResultString());
			}
			else {
				understandTextResult.offer("");
			}
		}

		@Override
		public void onError(SpeechError error) {
			if (null != error){
				logger.error("onError Code：" + error.getErrorCode());
			}
			understandTextResult.offer("");
		}
	};
	
	public static void main(String[] ags){
		
		new XunfeiService().tts("nannan","播放刘德华的忘情水","50","50","50");
	}
	
	
	public  void tts(String speaker,String text,String volume,String speed,String pitch){
		String filename = DigestUtils.md5Hex(speaker+text+speed+volume+pitch);
				
		SpeechSynthesizer speechSynthesizer = SpeechSynthesizer.createSynthesizer();
		// 设置发音人
		speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME,speaker);
		// 设置语速，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.SPEED,(speed));
		// 设置语调，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.PITCH,(pitch));
		// 设置音量，范围0~100
		speechSynthesizer.setParameter(SpeechConstant.VOLUME,(volume));
		// 设置合成音频保存位置（可自定义保存位置），默认保存在“./iflytek.pcm”
		speechSynthesizer.synthesizeToUri(text, new File(filename).getAbsolutePath(),new SynthesizeToUriListener(){

			@Override
			public void onBufferProgress(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSynthesizeCompleted(String ags, SpeechError error) {
				try {
					File srcFile = new File(filename);
					File targetFile = new File(filename+".wav");
					PcmToWavUtils.pcmToWave(srcFile,targetFile);
					
					OssUtils.upload(filename,targetFile);
					
					FileUtils.deleteQuietly(srcFile);
					FileUtils.deleteQuietly(targetFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(error!=null){
					error.printStackTrace();
				}
			}
			
		});
		
		
	}
 
}
