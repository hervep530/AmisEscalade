package com.ocherve.jcm;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.model.Cotation;
import com.ocherve.jcm.model.Site;

/**
 * Service to create, delete Site and store id created in static object dedicated for tests
 * 
 * @author herve_dev
 *
 */
public class SiteManager {

	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static final String[][] SITES_DE_TEST = new String[][] {
		{"Autoire", "France", "Lot", "Autoire est un spot du lot", "true", "false","true","false",
		"5", "25", "Est / Sud-Est", "271", "5a", "8b", "1", "false",
		"/media/site/autoire.jpg",
		"<p>Un quart des voies d’escalade dans le Lot se trouve à Autoire. Cela devrait suffire pour parler d’un site majeur, avec 271 lignes tracées sur un superbe calcaire, et pas mal de nouveautés venues enrichir et redynamiser les lieux au cours de la dernière décennie (après une longue période de léthargie). À site majeur, cadre majeur, avec la cascade de trente mètres qui tombe au fond de la gorge entaillant le causse, et le vieux village d’Autoire, avec ses belles pierres, sorti tout droit et intact de l’époque médiévale... Si ça c’est pas du décor de cinéma ! C’est donc dans ce décor que se déroule, émergeant des chênes, longue d’un kilomètre, la barre rocheuse d’Autoire. Elle démarre sous le belvédère de Siran, par lequel se fait l’accès, et tous les secteurs se succèdent, à mi-hauteur, avec un accès aisé de barre en barre. Le rocher prend plus ou moins de dévers, change de couleur et de forme, au gré de l’inspiration des éléments et de l’usure du temps. Grimpeurs ou non, tant d’esthétique fait forcément vibrer une corde sensible. Pour l’escalade, le premier à avoir vibré est Patrick Moissinac, qui, en 1983, a équipé les premières voies au secteur Bolivaria, le plus court. Le site est donc suffisamment âgé pour avoir connu la grande époque du fluo et des imprimés flashy et improbables, mais il est toujours resté au goût du jour et en phase avec son époque (heureusement, car celle des années 80 était assez fatigante pour les yeux), sans bien sûr passer à côté de l’actuel engouement pour les colonnes et les jolies voies dures en dévers. Le tout dans une région assez tranquille et un brin sauvage, ce qui garantit la quasi-absence de patine sur les voies. En contrepartie, pour les soirées qui déchirent et les after animés, ce n’est pas... majeur.\n" + 
		" Rocher & EscaladeAutoire Dalle, de léger à gros dévers, réglettes, trous, énormes colos, et quelques souvenirs de l’époque du bricolage qui a aussi laissé des traces ici. Une escalade qui fait la fête aux avant-bras. Force et rési courte de rigueur. Pour venir grimper, choisissez, pour les doigts, un modèle en titane, prenez un rouleau de peau de rechange, la technique que vous réservez aux grandes occasions, une panoplie de crochetages en tous genres (genoux, talons, pintes, tête, dos...) pour ruser dans les nombreuses colonnes. Les voies ne sont pas homogènes, il y a toujours un crux qui durcit la cotation. Au menu : 271 voies, de 5 à 25 m, du 5a au 8b. Prévoir 12 dégaines + corde de 60 m.\n" + 
		"Best of Après mûres concertations et débats participatifs, les grimpeurs locaux vous révèlent, non sans quelque réticence : \"Projet à Nicky\", \"Variante de la tristesse\", \"L’entre deux\", \"Gare aux gorets\".</p>"},
		{"Cantobre", "France", "Aveyron", "Cantobre est un spot de l'aveyron", "true", "false","true","false",
		"20", "40", "Sud / Sud-Est / Est", "55", "5c", "9a", "1", "false",
		"/media/site/cantobre.jpg",
		"<p>Cantobre est un village perché, petit détail qui est, à lui seul, de très bon augure. Ensuite, il est très beau, et même parmi les plus beaux de France, parfaitement restauré là-haut, sur son promontoire rocheux. Eh oui, vous avez bien lu, et qui dit promontoire rocheux dit falaise quelque part. Elle est là, juste en face, et c’est en la contemplant ainsi que son nom, la Brocante, prendra tout son sens. En effet, \"Brocante\" n’est autre que l’anagramme de Cantobre, et il est vrai que depuis le village, celle-ci ressemble à un étal de marché avec ses nombreuses lignes colorées. Comme dans toutes les brocantes, c’est en chinant dans les moindres recoins que l’on trouve son bonheur. Et ici, pour un grimpeur averti, il y a de quoi repartir satisfait de ses trouvailles. Le secteur principal, fut, on s’en doute avec un nom comme ça, le premier équipé, tellement agréable avec sa vire spacieuse et son point de vue sur Cantobre. Il resta secret pendant des années, terrain de jeu haut de gamme des grimpeurs locaux, non dénués de flair, il faut l’admettre, avec en particulier Yvan Sorro, le fin limier qui dénicha cette falaise. C’est plus tard, notamment avec le Roc Trip (2004), que de nouvelles voies ont vu le jour, permettant aujourd’hui de grimper à tous les étages. Et non à tous les niveaux... car celui de Cantobre est dans l’ensemble assez élevé.\n" + 
		" Rocher & EscaladeCantobre La marque de fabrique de ce calcaire magnifique, c’est toute la petite famille Trou (et les fermetures de bras qui s’en suivent) : mono, bi, tri, ils sont tous là. Mais on pourra quand même aussi arpenter de jolies colonnes, crisper des réglettes en tout genre ainsi que des plats redoutables. Plus de 50 voies, du 5c au 9a, réparties sur plusieurs falaises. La hauteur des voies est généralement de 20 m, mais certaines envolées culminent à 40 m maximum. Secteur \"Roi du pad\" (premier étage) : voies de 6c à 8a, assez courtes et souvent résistantes dans un profil vertical ou un peu déversant. Mieux vaut savoir arquer les prises. Secteur \"Barre Principale\" : c’est celui des gros muscles, avec une trentaine de voies alignées et des difficultés croissantes, du 7a+ au 8c. Les premiers mètres sont souvent les plus déversants (et cela s’intensifie du bord droit au bord gauche de la falaise), et vous manquerez peut-être un peu de décontraction pour fredonner la chanson de Gainsbourg \"Le poinçonneur des lilas\", même si cela serait de circonstance (pour les p’tits trous...). Secteur \"Jo Barre Team\" : on y trouve les voies plus faciles qui manquaient à Cantobre dans un mur vertical d’environ 40 m de hauteur, criblé de trous. Du 5c au 7b+.\n" + 
		"Best of . \"Soirée mousse\", 5c+. . \"Jo barre team\", 6c. . \"Les mains savonneuses\", 7b. . \"Turbulence\", 7b+, 35 m de continuité sur trous, très \"Made in Cantobre\". . \"Gazoline\", 7c+. . “VO2 max\", 8a+. . \"Mission impossible\", 8a/a+, et son jeté rendu célèbre par Gérôme Pouvreau lors du Roc Trip. . \"La Breloque\", 8b+, en 15 mouvements, ultra physique et court.</p>"},
		{"Kerlouan", "France", "Finistere", "Kerlouan est un spot du finistère", "true", "true","false","false",
		"0", "10", "Toutes", "1000", "3", "8a", "1", "false",
		"/media/site/kerlouan.jpg",
		"<p>Kerlouan, c’est cette pointe bretonne du Finistère nord, qui a comme première voisine la ville de New York. Entre Kerlouan et les Seychelles, la seule différence, c’est quelques degrés et les cocotiers en moins. Sinon, il y a ces mêmes blocs magnifiques éparpillés sur la plage, façonnés par le vent et les embruns en une profusion de lignes où vous pourrez grimper les pieds dans l’eau en compagnie des goélands. Ambiance \"grand large\" donc pour le bloc à Kerlouan, sur un site rythmé par le va-et-vient incessant de la mer. Ajoutez la beauté des paysages, la tranquillité du lieu, et la quantité et qualité des blocs ouverts, et voilà tout ce qui fait de ce bout de côte un peu excentré un site de bloc de grande qualité. Sur six kilomètres de rivage, des boules de granit posées partout sur le sable: plus de deux mille passages ouverts, répartis sur neuf secteurs, la plus grande concentration se trouvant sur Meneham et le secteur Crémiou, qui propose notamment un chaos à droite composé de nombreux blocs faciles, pas trop haut avec du sable en réception, histoire de prendre, en arrivant, la température du spot.\n" + 
		" Rocher & EscaladeKerlouan Quelques spécificités de Kerlouan:\n" + 
		"- Le granit breton. De quoi effrayer les plus douillets rien qu’en prononçant le mot… OK, c’est vrai, le grain est quelque peu abrasif. Voire agressif. Ici, peut-être un peu plus qu’ailleurs, il vous faudra gérer la peau de vos doigts. La météo quotidienne vous simplifiera peut-être les choses pour grimper un jour sur deux… Sinon, se tourner vers les lignes polies par le vent et la mer, douceur assurée! \n" + 
		"- Le style de grimpe. Des plats, des plats et des plats. De la compression à tous les étages, peu de réglettes, ne parlons même pas des prises de pieds, le plus souvent inexistantes. cantonade à leurs amis \"Arrêtez-vous à la maison en passant!\". Le Finistère, on n’y \"passe\" pas, parce que de l’autre côté il n’y a que la mer. On y va exprès. Du coup, en général on y reste un peu. Ça tombe bien: Kerlouan, à une quarantaine de kilomètres au nord de Brest, est une chouette destination de vacances.</p>"},
		{"Les Gorges du Loup", "France", "Alpes Maritimes", "Les Gorges du loup sont un port des Alpes Maritimes", "true", "false","true","false",
		"0", "45", "Ouest", "70", "6a", "9a", "1", "false",
		"/media/site/gorges_du_loup.jpg",
		"<p>Entre les reliefs du Mercantour et l’immensité bleue de la Méditerranée, avec le charme de l’arrière pays niçois et de ses villages perchés, et les eaux vives du Loup en fond de vallée, voilà un secteur entier qui regorge (du Loup) de caillou à se mettre sous les doigts. En plus, les choses ont beaucoup bougé ces vingt dernières années et les ouvreurs se sont bien démenés pour fouiner et explorer ce potentiel, véritable coffret à bijoux pour grimpeurs de tous niveaux, du débutant au très fort. Pour les accros des voies extrêmes et pour faire monter l’adrénaline jusqu’au 9a, c’est au secteur Déversé qu’il faut se donner rendez-vous. La Mecque du haut niveau dans les Alpes-Maritimes. Le secteur est parfois nommé Pupuce Surplomb, du nom de sa première voie d’artif. Le site Déversé, qui trahit un peu le programme rien que dans son nom, est un secteur de très haute difficulté. Comme si le nom ne suffisait pas, voici les statistiques : une cinquantaine de voies dans le huitième degré, et rien moins que trois voies cotées 9a : \"KinematiX\" (première d’Andreas Bindhammer en 2001), \"Abyss\" (ouverture et première par Alex Chabot en 2006, décotée depuis à 8c+), et \"Punt’X\" (ouvert par Cédric Lo Piccolo, et \"first ascent\" par Alex Chabot en 2007). Tout cela semble très moderne, mais il est bon de savoir que la première voie de libre du secteur était \"Déversé Satanique\". Ouverte par Bernard Duterte au milieu des années 1980, elle est cotée aujourd’hui 8a+, et malgré son grand âge, reste une voie majeure et incontournable. L’avantage d’un secteur très difficile où l’on risque de suer un peu et d’avoir chaud, c’est quand il est climatisé, comme au secteur Déversé, où même quand il fait plus de 30 degrés sur la côte, il fait généralement bon grâce à l’ombre et au petit courant d’air bienvenu qui souffle dans les Gorges du Loup.\n" + 
		" Rocher & EscaladeLes Gorges du Loup Le site compte 70 voies, de 6a à 9a (en incluant les variantes et connexions). L’escalade est un mélange de colonnettes infernales, de bidoigts et de prises taillées, où tout va très vite, en l’absence de repos dignes de ce nom. Les voies déversantes et très physiques permettent d’enchaîner les passages car on a plus affaire à de la continuité qu’à des problèmes de bloc. L’autre alternative à l’enchaînement, c’est la chute, mais les surplombs sont très bienveillants et le trou dans l’air n’a jamais occasionné trop d’égratignures. L'équipement est bon et la majorité des dégaines restent à demeure. Le pied de falaise est plutôt convivial.</p>"},
		{"Medonnet", "France", "Haute-Savoie", "Medonnet est un spot de Haute-Savoie", "false", "true","false","false",
		"0", "5", "Toutes", "250", "4", "8a", "1", "false",
		"/media/site/medonnet.jpg",
		"<p>Ce n’est pas banal, en terre promise de la grande voie rocheuse, d’aller grimper sur des rochers lilliputiens qui font figure de gravillons au pied des grandes faces mondialement célèbres. Mais chacun ses goûts. Et à deux pas du massif du Mont-Blanc, sur les balcons de Sallanches, le spot de Médonnet est fort apprécié des bloqueurs haut-savoyards. Ils ont beau s’user la peau sur ces blocs, ils en redemandent. Longtemps réservé à quelques initiés, ce spot proche de Combloux est désormais mieux connu. Bien à l’abri des regards dans son écrin de fayards, Médonnet ne laisse pas indifférent. Dès l’entrée dans la forêt, la magie opère. Peut-être ces quelques ressemblances avec LA forêt… Ce n’est d’ailleurs certainement pas un hasard si c’est un ancien Bleausard, Emmanuel Ratouis, qui a donné un second souffle au site en ouvrant et en répertoriant de nombreux passages. Passé le coup de foudre, on découvre vite qu’il y en a pour tous les goûts. C’est d’ailleurs l’un des intérêts du site: on peut grimper en famille et vraiment passer une superbe journée. La diversité des secteurs et l’étendue du site en font le spot majeur de la vallée de l’Arve.</p>"}
	};
	private static Integer[] ids;
	private static SiteDao dao;
	
	private static void initialization() {
		if ( dao != null ) return;
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG, "Initialization of Site Manager");
		dao = (SiteDao) DaoProxy.getInstance().getSiteDao();
	}

	/**
	 * Create sites for tests
	 */
	public static void create() {
		initialization();
		ids = new Integer[SITES_DE_TEST.length];
		try {
			for (int u = 0 ; u < SITES_DE_TEST.length ; u++) {
				Site site = new Site(SITES_DE_TEST[u][0], SITES_DE_TEST[u][1], SITES_DE_TEST[u][2],
					SITES_DE_TEST[u][3], Boolean.valueOf(SITES_DE_TEST[u][4]), Boolean.valueOf(SITES_DE_TEST[u][5]),
					Boolean.valueOf(SITES_DE_TEST[u][6]), Boolean.valueOf(SITES_DE_TEST[u][7]), Integer.valueOf(SITES_DE_TEST[u][8]),
					Integer.valueOf(SITES_DE_TEST[u][9]), SITES_DE_TEST[u][10], Integer.valueOf(SITES_DE_TEST[u][11]),
					Cotation.valueOf(SITES_DE_TEST[u][12]), Cotation.valueOf(SITES_DE_TEST[u][13]), 
					UserManager.getDao().get(1), Boolean.valueOf(SITES_DE_TEST[u][7]));
				//Integer.valueOf(SITES_DE_TEST[u][14])
				site.setContent(SITES_DE_TEST[u][17]);
				
				dao.create(site);
				ids[u] = site.getId();
			}			
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on creating sites"));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));
		}		
	}
	
	/**
	 * Delete site dedicated for test
	 */
	public static void delete() {
		initialization();
		try {
			for (int u = 0 ; u < ids.length ; u++) {			
				dao.delete(ids[u]);
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on deleting site"));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));
		}
	}

	/**
	 * Delete sites
	 */
	public static void deleteAll() {
		initialization();
		List<Site> sites= dao.getList();
		try {
			for (Site site : sites) {			
				dao.delete(site.getId());
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on deleting site"));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));
		}
	}

	/**
	 * @return ids of site created as Integer array
	 */
	protected static Integer[] getIds() {
		return ids;
	}
	
	protected static SiteDao getDao() {
		initialization();
		return dao;
	}	
	
	/**
	 * @param sites List of site
	 * @param siteExpected string to describe kind of list expected (where clause)
	 */
	public static void LogSiteList(List<Site> sites, String siteExpected) {
		initialization();
		String message = "%n Display list of " + siteExpected + " in database%n";
		message += "Id / Site name / Departement / Cotation Min / Cotation max / Auteur / Date creation%n";
		
		for (Site site : sites) {
			message += site.getType().toString() + " " + site.getId() + " / ";
			message += site.getName() + " / ";
			message += site.getDepartment() + " / ";
			message += site.getCotationMin().getLabel() + " / ";
			message += site.getCotationMax().getLabel() + " / ";
			message += site.getAuthor().getUsername() + " / ";
			message += site.getTsCreated().toString() + "%n";
		}
		DLOG.log(Level.DEBUG, String.format(message));
	}

	/**
	 * Display condensed list of site in log for debug
	 * @param id valid index of Integer array ids
	 */
	public static void logSite (Integer id) {
		initialization();
		Site site = dao.get(id);
		logSite(site);
	}

	/**
	 * @param site
	 */
	public static void logSite (Site site) {
		initialization();
		String message = "%n";
		message += "Site id : " + site.getType().toString() + " " + site.getId() + "%n";
		message += "Site name : " + site.getName() + "%n";
		message += "Site slug : " + site.getSlug() + "%n";
		message += "Departement : " + site.getDepartment() + "%n";
		message += "Pays : " + site.getCountry() + "%n";
		message += "Bloc : " + String.valueOf(site.isBlock()) + "%n";
		message += "Falaise : " + String.valueOf(site.isCliff()) + "%n";
		message += "Mur : " + String.valueOf(site.isWall()) + "%n";
		message += "Orientation : " + site.getOrientation() + "%n";
		message += "Hauteur mini : " + site.getMinHeight() + "%n";
		message += "Hauteur maxi : " + site.getMaxHeight() + "%n";
		message += "Nombre de voies : " + site.getPathsNumber() + "%n";
		message += "Cotation mini : " + site.getCotationMin().getLabel() + "%n";
		message += "Cotation maxi : " + site.getCotationMax().getLabel() + "%n";
		String content = site.getContent();
		if ( content.length() > 70) content = content.substring(0, 70);
		message += "Content : " + content + "...%n";
		message += "Auteur : " + site.getAuthor().getUsername() + "%n";
		message += "Créé le : " + site.getTsCreated().toString() + "%n";
		message += "Modifié le : " + site.getTsModified().toString() + "%n";
		message += "Tagué ami : " + String.valueOf(site.isFriendTag()) + "%n";
		message += "Published : " + String.valueOf(site.isPublished()) + "%n";
		DLOG.log(Level.DEBUG, String.format(message));
	}


	
}

