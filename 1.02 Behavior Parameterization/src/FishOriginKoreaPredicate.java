public class FishOriginKoreaPredicate implements FishPredicate {
    @Override
    public boolean test(Fish fish) {
        return "Korea".equals(fish.getOrigin());
    }
}
