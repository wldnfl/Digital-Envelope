package repository;

import model.Report;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.Map;

public class ReportRepository {
	private static ReportRepository instance = new ReportRepository();

	private Map<String, Report> reports = new ConcurrentHashMap<>();

	private ReportRepository() {
	}

	public static ReportRepository getInstance() {
		return instance;
	}

	public Collection<Report> getAllReports() {
		return reports.values();
	}

	public void saveReport(Report report) {
		reports.put(report.getUniqueCode(), report);
	}

	public Report getReport(String uniqueCode) {
		return reports.get(uniqueCode);
	}

	public void updateReport(Report report) {
		reports.put(report.getUniqueCode(), report);
	}
}
