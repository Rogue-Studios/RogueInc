public class Factory {

    private String name;
    private boolean running;
    private boolean unlocked;
    private int numberOfResources; // was originally named 'resourceTypes'
    private Resource[] resources;
    private int[] resourcesQuantity;
    private double marketValue;
    private double productionTime;
    private double timeSinceProduction;

    public Factory(String name, Resource resource1, Resource resource2, Resource resource3, Resource resource4,
                   int resource1Quantity, int resource2Quantity, int resource3Quantity, int resource4Quantity,
                   double marketValue, double productionTime) {

        this.name = name;
        this.running = false;
        this.unlocked = false;

        if(resource3Quantity == 0) {
            numberOfResources = 2;
        } else if (resource4Quantity == 0) {
            numberOfResources = 3;
        } else {
            numberOfResources = 4;
        }

        resources = new Resource[numberOfResources];
        resourcesQuantity = new int[numberOfResources];


        this.resources[0] = resource1;
        this.resources[1] = resource2;

        this.resourcesQuantity[0] = resource1Quantity;
        this.resourcesQuantity[1] = resource2Quantity;

        // Fixes null pointer exception with arrays
        if(numberOfResources > 2) {
            this.resources[2] = resource3;
            this.resourcesQuantity[2] = resource3Quantity;

            if(numberOfResources  == 4) {
                this.resources[3] = resource4;
                this.resourcesQuantity[3] = resource4Quantity;
            }
        }

        this.marketValue = marketValue;
        this.productionTime = productionTime;
        this.timeSinceProduction = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void setNumberOfResources(int numberOfResources) {
        this.numberOfResources = numberOfResources;
    }

    public void setResources(Resource[] resources) {
        this.resources = resources;
    }

    public void setResourcesQuantity(int[] resourcesQuantity) {
        this.resourcesQuantity = resourcesQuantity;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public void setProductionTime(double productionTime) {
        this.productionTime = productionTime;
    }

    public void setTimeSinceProduction(double timeSinceProduction) {
        this.timeSinceProduction = timeSinceProduction;
    }

    public String getName() {
        return name;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public int getNumberOfResources() {
        return numberOfResources;
    }

    public Resource[] getResources() {
        return resources;
    }

    public int[] getResourcesQuantity() {
        return resourcesQuantity;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public double getProductionTime() {
        return productionTime;
    }

    public double getTimeSinceProduction() {
        return timeSinceProduction;
    }
}
