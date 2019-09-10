package net.sunrise.manager.system;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.sunrise.common.CommonConstants;
import net.sunrise.common.CommonUtility;
import net.sunrise.common.GUUISequenceGenerator;
import net.sunrise.common.ListUtility;
import net.sunrise.domain.entity.system.SystemSequence;
import net.sunrise.framework.component.RootComponent;
import net.sunrise.service.api.system.SystemSequenceService;

@Component
@Transactional
public class SystemSequenceManager extends RootComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9017132574243525245L;

	@Inject
	private SystemSequenceService businessService;

	public String requestSerialNo(String objectName){
		SystemSequence nextSequence = queryNextSequence(objectName);//GUUISequenceGenerator.getInstance().nextGUUId(objectType);

		return 
				new StringBuilder(objectName.toUpperCase())
				.append(String.format("%12d", nextSequence.getValue().longValue()))
				.toString();
	}

	private SystemSequence queryNextSequence(String objectName){
		SystemSequence systemSequence = businessService.getOne(objectName);
		if (null==systemSequence){
			systemSequence = SystemSequence.builder()
					.name(objectName)
					.value(CommonConstants._sequenceMinValue)
					.build();
		}
		systemSequence.increase();
		persistSequence(systemSequence);
		return systemSequence;
	}

	@Async
	private void persistSequence(SystemSequence systemSequence){
		businessService.saveOrUpdate(systemSequence);
		log.info("Persist sequence [" + systemSequence.getName() + "] value [" + systemSequence.getValue() + "]");
	}

	@Async
	private void persistSequenceMap(String objectName){
		SystemSequence systemSequence = businessService.getOne(objectName);
		systemSequence.increase();
		businessService.saveOrUpdate(systemSequence);
		System.out.println("Inside persist sequence map @" + Calendar.getInstance().getTime());
	}

	public SystemSequence registerSequence(String sequentialNumber) {
		long value = CommonUtility.extractEmbeddedNumber(sequentialNumber);
		int firstIdx = sequentialNumber.indexOf(String.valueOf(value));
		String sequence = sequentialNumber.substring(0, firstIdx);
		return registerSequence(sequence, value);
	}

	public SystemSequence registerSequence(String sequence, long value) {
		SystemSequence regSystemSequence = businessService.getOne(sequence);
		if (null==regSystemSequence){
			regSystemSequence = SystemSequence.builder()
					.name(sequence)
					.value(Long.valueOf(value))
					.build();
		} else {
			regSystemSequence.setValue(value);
		}
		return businessService.saveOrUpdate(regSystemSequence);
	}

	public void initializeSystemSequence(){
		Map<String, Long> guuidMap = ListUtility.createMap();
		List<SystemSequence> systemSequences = businessService.getObjects();
		for (SystemSequence systemSequence :systemSequences){
			guuidMap.put(systemSequence.getName(), systemSequence.getValue());
		}

		GUUISequenceGenerator.getInstance().initiatedStartedNumber(guuidMap);
	}
}
