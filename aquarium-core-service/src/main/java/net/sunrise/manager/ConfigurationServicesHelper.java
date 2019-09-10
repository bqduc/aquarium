/**
 * 
 */
package net.sunrise.manager;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import net.sunrise.cdx.domain.entity.Configuration;
import net.sunrise.cdx.domain.entity.ConfigurationDetail;
import net.sunrise.cdx.manager.ConfigurationDetailManager;
import net.sunrise.cdx.manager.ConfigurationManager;
import net.sunrise.common.CommonConstants;
import net.sunrise.common.DateTimeUtility;
import net.sunrise.common.GenderTypeUtility;
import net.sunrise.domain.entity.hc.Employee;
import net.sunrise.domain.enums.EmployeeConfigurationModel;
import net.sunrise.enums.DefaultConfigurations;
import net.sunrise.exceptions.EcosysException;
import net.sunrise.global.WebAdminConstants;
import net.sunrise.helper.GlobalDataServiceHelper;
import net.sunrise.hrcx.manager.EmployeeManager;
import net.sunrise.model.Bucket;

/**
 * @author bqduc
 *
 */
@Service
public class ConfigurationServicesHelper {
	@Inject
	private ConfigurationManager configurationManager;

	@Inject
	private ConfigurationDetailManager configurationDetailManager;

	@Inject
	private GlobalDataServiceHelper globalDataServiceHelper;
	
	@Inject
	private EmployeeManager employeeManager;

	/*private List<String> malePatternList = new ArrayList<>();
	private List<String> femalePatternList = new ArrayList<>();*/

	public void importInventoryItems() throws EcosysException {
		try {
		} catch (Exception e) {
			throw new EcosysException(e);
		}
	}

	
	public List<Employee> importEmployees() throws EcosysException {
		List<Employee> employees = new ArrayList<>();
		Bucket dataBucket = null;
		Employee emp = null;
		ClassPathResource classPathResource = null;
		try {
			classPathResource = new ClassPathResource(EmployeeConfigurationModel.DataSource.getModel());
			
			dataBucket = globalDataServiceHelper.readSpreadsheetData(
					new FileInputStream(classPathResource.getFile()), 
					new String[]{EmployeeConfigurationModel.DataSource.getDataPattern()});

			List<List<String>> dataStrings = (List<List<String>>)dataBucket.getBucketData().get(EmployeeConfigurationModel.DataSource.getDataPattern());
			for (int i = EmployeeConfigurationModel.DataSource.getIndex(); i < dataStrings.size(); ++i){
				emp = this.parseEmployee(dataStrings.get(i));
				try {
					if (1 > employeeManager.getCountByCode(emp.getCode())){
						employeeManager.save(emp);
						employees.add(emp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		return employees;
	}

	private Employee parseEmployee(List<String> dataParts) throws EcosysException {
		Employee emp = new Employee();
		Date defaultDate = DateTimeUtility.getSystemDate();
		try {
			emp
			.setCode(dataParts.get(EmployeeConfigurationModel.Code.getIndex()))
			.setFirstName(dataParts.get(EmployeeConfigurationModel.FirstName.getIndex()))
			.setLastName(dataParts.get(EmployeeConfigurationModel.LastName.getIndex()))
			.setEmail(dataParts.get(EmployeeConfigurationModel.Email.getIndex()))
			.setAddress(dataParts.get(EmployeeConfigurationModel.Address.getIndex()))
			.setPhone(dataParts.get(EmployeeConfigurationModel.Phone.getIndex()))
			.setPhoneMobile(dataParts.get(EmployeeConfigurationModel.PhoneMobile.getIndex()))
			.setEmail(dataParts.get(EmployeeConfigurationModel.Email.getIndex()))
			.setGender(GenderTypeUtility.getGenderType(dataParts.get(EmployeeConfigurationModel.Gender.getIndex())))

			.setDateOfBirth(DateTimeUtility.getDateInstance(dataParts.get(EmployeeConfigurationModel.DateOfBirth.getIndex()), defaultDate))
			.setPlaceOfBirth(dataParts.get(EmployeeConfigurationModel.PlaceOfBirth.getIndex()))

			.setMaritalStatus(dataParts.get(EmployeeConfigurationModel.MaritalStatus.getIndex()))
			.setEducation(dataParts.get(EmployeeConfigurationModel.Education.getIndex()))
			.setFreignLanguages(dataParts.get(EmployeeConfigurationModel.ForeignLanguage.getIndex()))
			.setWorkExperiences(dataParts.get(EmployeeConfigurationModel.WorkExperiences.getIndex()))

			.setNationality(dataParts.get(EmployeeConfigurationModel.Nationality.getIndex()))
			.setNationalId(dataParts.get(EmployeeConfigurationModel.NationalId.getIndex()))
			.setNationalIdIssueDate(DateTimeUtility.getDateInstance(dataParts.get(EmployeeConfigurationModel.NationalIdIssueDate.getIndex()), defaultDate))
			.setNationalIdIssuePlace(dataParts.get(EmployeeConfigurationModel.NationalIdIssuePlace.getIndex()))
			.setNationalIdExpiredDate(DateTimeUtility.getDateInstance(dataParts.get(EmployeeConfigurationModel.NationalIdExpiredDate.getIndex()), defaultDate))

			.setDescription(dataParts.get(EmployeeConfigurationModel.Info.getIndex()))
			;
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		return emp;
	}

	/*private GenderType getGenderType(String data){
		if (malePatternList.isEmpty()){
			malePatternList = Arrays.asList(EmployeeConfigurationModel.GenderTypeMale.getDataPattern().split(CommonConstants.SEMICOLON));
		}

		if (malePatternList.contains(data)){
			return GenderType.Male;
		}

		if (femalePatternList.isEmpty()){
			femalePatternList = Arrays.asList(EmployeeConfigurationModel.GenderTypeFemale.getDataPattern().split(CommonConstants.SEMICOLON));
		}

		if (femalePatternList.contains(data)){
			return GenderType.Female;
		}

		return GenderType.Unknown;
	}*/

	public Configuration setupEmployeeConfigurations() throws EcosysException {
		Optional<Configuration> optConfiguration = configurationManager.getByName(DefaultConfigurations.setupEmployees.getConfigurationName());
		Configuration config = null;
		if (!optConfiguration.isPresent()){
			config = Configuration.builder()
					.group(WebAdminConstants.CONFIG_GROUP_GENERAL)
					.name(DefaultConfigurations.setupEmployees.getConfigurationName())
					.value(CommonConstants.OPTION_DISABLED)
					.valueExtended(new StringBuilder(
							EmployeeConfigurationModel.DataSource.getModel())
							.append(";")
							.append(EmployeeConfigurationModel.DataSource.getDataPattern())
							.append(";")
							.append(EmployeeConfigurationModel.DataSource.getIndex()).toString())
					.build()
					;

			configurationManager.save(config);
		} else {
			config = optConfiguration.get();
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Code.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Code.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Code.getIndex()), 
							EmployeeConfigurationModel.Code.getDataPattern()));
		}
		
		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.FirstName.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.FirstName.getModel(), 
							String.valueOf(EmployeeConfigurationModel.FirstName.getIndex()), 
							EmployeeConfigurationModel.FirstName.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.LastName.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.LastName.getModel(), 
							String.valueOf(EmployeeConfigurationModel.LastName.getIndex()), 
							EmployeeConfigurationModel.LastName.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.DateOfBirth.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.DateOfBirth.getModel(), 
							String.valueOf(EmployeeConfigurationModel.DateOfBirth.getIndex()), 
							EmployeeConfigurationModel.DateOfBirth.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.PlaceOfBirth.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.PlaceOfBirth.getModel(), 
							String.valueOf(EmployeeConfigurationModel.PlaceOfBirth.getIndex()), 
							EmployeeConfigurationModel.PlaceOfBirth.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.NationalId.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.NationalId.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalId.getIndex()), 
							EmployeeConfigurationModel.NationalId.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.NationalIdIssueDate.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.NationalIdIssueDate.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdIssueDate.getIndex()), 
							EmployeeConfigurationModel.NationalIdIssueDate.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.NationalIdIssuePlace.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.NationalIdIssuePlace.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdIssuePlace.getIndex()), 
							EmployeeConfigurationModel.NationalIdIssuePlace.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.NationalIdExpiredDate.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.NationalIdExpiredDate.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdExpiredDate.getIndex()), 
							EmployeeConfigurationModel.NationalIdExpiredDate.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Address.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Address.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Address.getIndex()), 
							EmployeeConfigurationModel.Address.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Phone.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Phone.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Phone.getIndex()), 
							EmployeeConfigurationModel.Phone.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.PhoneMobile.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.PhoneMobile.getModel(), 
							String.valueOf(EmployeeConfigurationModel.PhoneMobile.getIndex()), 
							EmployeeConfigurationModel.PhoneMobile.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Email.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Email.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Email.getIndex()), 
							EmployeeConfigurationModel.Email.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Gender.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Gender.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Gender.getIndex()), 
							EmployeeConfigurationModel.Gender.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.MaritalStatus.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.MaritalStatus.getModel(), 
							String.valueOf(EmployeeConfigurationModel.MaritalStatus.getIndex()), 
							EmployeeConfigurationModel.MaritalStatus.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Education.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Education.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Education.getIndex()), 
							EmployeeConfigurationModel.Education.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Info.getModel())){
			config.addConfigurationDetail(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Info.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Info.getIndex()), 
							EmployeeConfigurationModel.Info.getDataPattern()));
		}

		configurationManager.save(config);

		/*if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.FirstName.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.FirstName.getModel(), 
							String.valueOf(EmployeeConfigurationModel.FirstName.getIndex()), 
							EmployeeConfigurationModel.FirstName.getDataPattern()));
		}
		
		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.LastName.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.LastName.getModel(), 
							String.valueOf(EmployeeConfigurationModel.LastName.getIndex()), 
							EmployeeConfigurationModel.LastName.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.DateOfBirth.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.DateOfBirth.getModel(), 
							String.valueOf(EmployeeConfigurationModel.DateOfBirth.getIndex()), 
							EmployeeConfigurationModel.DateOfBirth.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.PlaceOfBirth.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.PlaceOfBirth.getModel(), 
							String.valueOf(EmployeeConfigurationModel.PlaceOfBirth.getIndex()), 
							EmployeeConfigurationModel.PlaceOfBirth.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.NationalId.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.NationalId.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalId.getIndex()), 
							EmployeeConfigurationModel.NationalId.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.NationalIdIssueDate.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.NationalIdIssueDate.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdIssueDate.getIndex()), 
							EmployeeConfigurationModel.NationalIdIssueDate.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.NationalIdIssuePlace.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.NationalIdIssuePlace.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdIssuePlace.getIndex()), 
							EmployeeConfigurationModel.NationalIdIssuePlace.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.NationalIdExpiredDate.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.NationalIdExpiredDate.getModel(), 
							String.valueOf(EmployeeConfigurationModel.NationalIdExpiredDate.getIndex()), 
							EmployeeConfigurationModel.NationalIdExpiredDate.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Address.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Address.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Address.getIndex()), 
							EmployeeConfigurationModel.Address.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Phone.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Phone.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Phone.getIndex()), 
							EmployeeConfigurationModel.Phone.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.PhoneMobile.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.PhoneMobile.getModel(), 
							String.valueOf(EmployeeConfigurationModel.PhoneMobile.getIndex()), 
							EmployeeConfigurationModel.PhoneMobile.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Email.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Email.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Email.getIndex()), 
							EmployeeConfigurationModel.Email.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Gender.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Gender.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Gender.getIndex()), 
							EmployeeConfigurationModel.Gender.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.MaritalStatus.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.MaritalStatus.getModel(), 
							String.valueOf(EmployeeConfigurationModel.MaritalStatus.getIndex()), 
							EmployeeConfigurationModel.MaritalStatus.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Education.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Education.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Education.getIndex()), 
							EmployeeConfigurationModel.Education.getDataPattern()));
		}

		if (null==configurationDetailManager.getByConfigurationAndName(config, EmployeeConfigurationModel.Info.getModel())){
			configurationDetailManager.save(
					ConfigurationDetail.getInstance(
							config, 
							EmployeeConfigurationModel.Info.getModel(), 
							String.valueOf(EmployeeConfigurationModel.Info.getIndex()), 
							EmployeeConfigurationModel.Info.getDataPattern()));
		}*/
		return config;
	}
}
