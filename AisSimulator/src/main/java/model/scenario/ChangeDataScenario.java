package model.scenario;

import java.util.ArrayList;

import dk.dma.ais.message.NavigationalStatus;
import dk.dma.ais.message.ShipTypeCargo;
import model.datavessel.AisVersion;
import model.datavessel.PositionFixType;
import model.datavessel.SpecialManIndicator;

/**
 * Modèle scénario permettant de modifier une valeur choisie
 * S'applique d'un instant défini pendant un certaine durée
 * Ne nécessite pas de re-calcule de la simulation
 */
public class ChangeDataScenario extends Scenario {

	/**
	 * Le type de donnée à changer
	 */
	private DataEnum type; 

	/**
	 * La valeur de la données
	 */
	private Object value;

	/**
	 * Sauvegarde des anciennes valeurs modifiées
	 */
	private ArrayList<Object> oldValue;

	public ChangeDataScenario(int startTime, int duration, DataEnum type, Object value) {
		super(startTime, duration, false);
		this.type = type;
		this.value = value;
		oldValue = new ArrayList<Object>();
	}

	/**
	 * Applique la modification en fonction du type sur la plage de valeurs définie
	 */
	@Override
	public void apply() {
		int id = 0;
		try { id = Integer.parseInt(((String)value).split(" ")[0]); } catch(NumberFormatException e){}
		switch(type) {
		case MMSI:
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getMmsi());
				getSimulation().getInstant(i).getStaticData().setMmsi(Integer.parseInt((String)value));
			} break;
		case IMO: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getImo());
				getSimulation().getInstant(i).getStaticData().setImo(Integer.parseInt((String)value)); 
			} break;
		case CALLSIGN: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getCallSign());
				getSimulation().getInstant(i).getStaticData().setCallSign((String)value); 
			} break;
		case NAME: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getName());
				getSimulation().getInstant(i).getStaticData().setName((String)value); 
			} break;
		case AIS_VERSION: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getAisVersion());
				getSimulation().getInstant(i).getStaticData().setAisVersion(AisVersion.getByID(id)); 
			} break;
		case SHIP_TYPE:	
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getShipType());
				getSimulation().getInstant(i).getStaticData().setShipType(new ShipTypeCargo(id)); 
			} break;
		case DIM_STERN: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getPosRef().getDimStern());
				getSimulation().getInstant(i).getStaticData().getPosRef().setDimStern(Integer.parseInt((String)value)); 
			} break;
		case DIM_BOW: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getPosRef().getDimBow());
				getSimulation().getInstant(i).getStaticData().getPosRef().setDimBow(Integer.parseInt((String)value)); 
			} break;
		case DIM_PORT: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getPosRef().getDimPort());
				getSimulation().getInstant(i).getStaticData().getPosRef().setDimPort(Integer.parseInt((String)value)); 
			} break;
		case DIM_STARBOARD: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getPosRef().getDimStarboard());
				getSimulation().getInstant(i).getStaticData().getPosRef().setDimStarboard(Integer.parseInt((String)value)); 
			} break;
		case POS_FIX_TYPE: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getPosType());
				getSimulation().getInstant(i).getStaticData().setPosType(PositionFixType.getByID(id)); 
			} break;
		case DRAUGHT:
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getDraught());
				getSimulation().getInstant(i).getStaticData().setDraught(Integer.parseInt((String)value)); 
			} break;
		case DTE: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().isDte());
				getSimulation().getInstant(i).getStaticData().setDte(id == 1 ? false:true); 
			} break;
		case DESTINATION: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getStaticData().getDestination());
				getSimulation().getInstant(i).getStaticData().setDestination((String)value); 
			} break;
		case NAV_STATS: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getDynamicData().getNavStat());
				getSimulation().getInstant(i).getDynamicData().setNavStat(NavigationalStatus.get(id)); 
			} break;
		case ROT: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getDynamicData().getRot());
				getSimulation().getInstant(i).getDynamicData().setRot(Integer.parseInt((String)value)); 
			} break;
		case POS_ACC: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getDynamicData().getPosAcc());
				getSimulation().getInstant(i).getDynamicData().setPosAcc(Integer.parseInt((String)value)); 
			} break;
		case LATITUDE: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getDynamicData().getPosition().getLatitudeDouble());
				getSimulation().getInstant(i).getDynamicData().getPosition().setLatitude(Double.parseDouble((String)value)); 
			} break;
		case LONGITUDE: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getDynamicData().getPosition().getLongitudeDouble());
				getSimulation().getInstant(i).getDynamicData().getPosition().setLongitude(Double.parseDouble((String)value)); 
			} break;
		case COG: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getDynamicData().getCog());
				getSimulation().getInstant(i).getDynamicData().setCog(Integer.parseInt((String)value)); 
			} break;
		case TRUEHEADING: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getDynamicData().getTrueHeading());
				getSimulation().getInstant(i).getDynamicData().setTrueHeading(Integer.parseInt((String)value));
			} break;
		case SPECIAL_MAN_INDICATOR:
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getDynamicData().getSpecialManIndicator());
				getSimulation().getInstant(i).getDynamicData().setSpecialManIndicator(SpecialManIndicator.getByID(id)); 
			} break;
		case RAIM: 
			for(int i=getStartTime() ; i<getStartTime()+getDuration() ; i++) {
				oldValue.add(getSimulation().getInstant(i).getDynamicData().getRaim());
				getSimulation().getInstant(i).getDynamicData().setRaim(Integer.parseInt((String)value)); 
			} break;
		default:break;
		}
	}

	/**
	 * Restaure les anciennes valeurs en fonction du type sur la plage de valeurs modifiée et supprime le scénario
	 */
	@Override
	public void remove() {
		switch(type) {
		case MMSI:
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().setMmsi((int) oldValue.get(j));
			break;
		case IMO: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().setImo((int) oldValue.get(j));
			break;
		case CALLSIGN: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().setCallSign((String) oldValue.get(j));
			break;
		case NAME: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().setName((String) oldValue.get(j));
			break;
		case AIS_VERSION: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().setAisVersion((AisVersion) oldValue.get(j));
			break;
		case SHIP_TYPE:	
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().setShipType((ShipTypeCargo) oldValue.get(j));
			break;
		case DIM_STERN: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().getPosRef().setDimStern((int) oldValue.get(j));
			break;
		case DIM_BOW: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().getPosRef().setDimBow((int) oldValue.get(j));
			break;
		case DIM_PORT: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().getPosRef().setDimPort((int) oldValue.get(j));
			break;
		case DIM_STARBOARD: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().getPosRef().setDimStarboard((int) oldValue.get(j));
			break;
		case POS_FIX_TYPE: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().setPosType((PositionFixType) oldValue.get(j));
			break;
		case DRAUGHT:
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().setDraught((int) oldValue.get(j));
			break;
		case DTE: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().setDte((boolean) oldValue.get(j));
			break;
		case DESTINATION: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getStaticData().setDestination((String) oldValue.get(j));
			break;
		case NAV_STATS: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getDynamicData().setNavStat((NavigationalStatus) oldValue.get(j));
			break;
		case ROT: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getDynamicData().setRot((int) oldValue.get(j));
			break;
		case POS_ACC: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getDynamicData().setPosAcc((int) oldValue.get(j));
			break;
		case LATITUDE: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getDynamicData().getPosition().setLatitude((double) oldValue.get(j));
			break;
		case LONGITUDE: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getDynamicData().getPosition().setLongitude((double) oldValue.get(j));
			break;
		case COG: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getDynamicData().setCog((int) oldValue.get(j));
			break;
		case TRUEHEADING: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getDynamicData().setTrueHeading((int) oldValue.get(j));
			break;
		case SPECIAL_MAN_INDICATOR:
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getDynamicData().setSpecialManIndicator((SpecialManIndicator) oldValue.get(j));
			break;
		case RAIM: 
			for(int i=getStartTime(), j=0 ; i<getStartTime()+getDuration() ; i++, j++)
				getSimulation().getInstant(i).getDynamicData().setRaim((int) oldValue.get(j));
			break;
		default:break;
		}
		super.remove();
	}

	public DataEnum getType() {
		return type;
	}

	public void setType(DataEnum type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ChangeDataScenario [type=" + type + ", value=" + value + ", toString()=" + super.toString() + "]";
	}

	public String description() {
		return "Modification d'une donnée - Moment d'arrivée : "+getStartTime()+", Durée : "+getDuration()+", Donnée : "+type;
	}

}
