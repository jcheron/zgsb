package net.application;

import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletContextEvent;

import net.kernel.KFichefrais;
import net.kernel.KLignefraisforfait;
import net.kernel.KLignefraishorsforfait;
import net.kernel.KMenu;
import net.ko.db.KDataBase;
import net.ko.db.KDataBaseConnection;
import net.ko.db.KDbResultSet;
import net.ko.events.KEventListener;
import net.ko.events.KEventType;
import net.ko.events.KFireEvent;
import net.ko.framework.Ko;
import net.ko.kobject.KRecordStatus;
import net.ko.listeners.KoApplicationListener;

public class ApplicationListener extends KoApplicationListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.ko.listeners.KoApplicationListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		super.contextInitialized(contextEvent);
		Ko.addListener(KFichefrais.class, KEventType.etUpdateKobjectStart, new KEventListener() {

			@Override
			public void update(KFireEvent event) {

				KFichefrais ff = (KFichefrais) event.getObject();
				Calendar cal = Calendar.getInstance();
				ff.setDateModif(new java.sql.Date(cal.getTimeInMillis()));
				if ("VA".equals(ff.getIdEtat())) {
					KDataBase db = Ko.kdefaultDatabase(KFichefrais.class);
					ff.getConstraints().load(db);
					ff.updateMontants();
				}
			}
		});
		Ko.addListener(KMenu.class, KEventType.etLoadKobjectEnd, new KEventListener() {

			@Override
			public void update(KFireEvent event) {
				KMenu menu = (KMenu) event.getObject();
				KDbResultSet rs = (KDbResultSet) event.getParams()[0];
				String sqlCount = menu.getSqlCount();
				if (sqlCount != null && !"".equals(sqlCount)) {
					KDataBase db = null;

					Object o = null;
					try {
						db = new KDataBaseConnection(rs);
						o = db.getValue(sqlCount);
						if (o != null)
							menu.setCount(Integer.valueOf(o + ""));
					} catch (SQLException e) {
					}
				}

			}
		});
		Ko.addListener(KLignefraishorsforfait.class, KEventType.etUpdateKobjectStart, new KEventListener() {

			@Override
			public void update(KFireEvent event) {
				KLignefraishorsforfait lfhf = (KLignefraishorsforfait) event.getObject();
				if (lfhf.getMontant() < 0)
					lfhf.setRecordStatus(KRecordStatus.rsNone);

			}
		});

		Ko.addListener(KLignefraisforfait.class, KEventType.etLoadKobjectStart, new KEventListener() {

			@Override
			public void update(KFireEvent event) {
				Ko.setTempConstraintDeph(2);

			}
		});
		Ko.addListener(KLignefraisforfait.class, KEventType.etLoadKobjectEnd, new KEventListener() {

			@Override
			public void update(KFireEvent event) {
				KLignefraisforfait lff = (KLignefraisforfait) event.getObject();
				if (lff != null)
					if (lff.getFraisforfait() != null)
						lff.setTotal(lff.getFraisforfait().getMontant() * lff.getQuantite());
				Ko.restoreConstraintDeph();
			}
		});
		Ko.addListener(KFichefrais.class, KEventType.etLoadKlistObjectStart, new KEventListener() {
			@Override
			public void update(KFireEvent event) {
				Ko.setTempConstraintDeph(2);
			}
		});
		Ko.addListener(KFichefrais.class, KEventType.etLoadKlistObjectEnd, new KEventListener() {
			@Override
			public void update(KFireEvent event) {
				Ko.restoreConstraintDeph();
			}
		});
	}

}
