export interface Action {
  type: string;
  description: string;
  containerId?: string;
  targetId?: string;
  value?: string;
}

export interface ContainerSteps {
  container: string;
  steps: string[];
}

export interface Recipe {
  id: string;
  name: string;
  ingredients: string[];
  instructions: ContainerSteps[];
  code: string;
}
