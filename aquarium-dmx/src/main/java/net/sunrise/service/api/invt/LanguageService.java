package net.sunrise.service.api.invt;

import net.sunrise.domain.entity.config.Language;
import net.sunrise.framework.service.GenericService;

public interface LanguageService extends GenericService<Language, Long>{
	Language getByCode(String code);
	Language getByName(String name);
}
